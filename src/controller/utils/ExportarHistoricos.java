package controller.utils;

import controller.GerenciadorDosDadosImportados;
import controller.services.HistoricosService;
import model.Animal;
import model.Historico;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Classe responsável por implementar métodos para a exportação de históricos dos animais.
 */
public class ExportarHistoricos {

    /**
     * Persiste os hitóricos dos animais importados em um arquivo binário para que funcione como a base de dados do sistema.
     */
    public static void persistirHistoricosDosAnimais() {
        final List<Animal> animais = GerenciadorDosDadosImportados.getAnimais();
        final List<Historico> historicos = GerenciadorDosDadosImportados.getHistoricos();
        if (animais == null || animais.isEmpty() || historicos == null || historicos.isEmpty()) return;

        for (Animal animal : animais) {
            List<Historico> historicosDoAnimal = HistoricosService.encontrarHistoricosDeAnimal(animal);

            final Path pastaDados = Paths.get("resources/database");
            if (!pastaDados.toFile().exists()) {
                try {
                    Files.createDirectory(pastaDados);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, String.format("Não foi possível criar a pasta para armazenar os históricos dos animais.%n%s", ex.toString()), "ERRO!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            try (ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(Paths.get(String.format("%s/%s.dat", pastaDados.toAbsolutePath(), animal.getIdentificador())).toFile()))) {
                file.reset();
                file.writeObject(historicosDoAnimal);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, String.format("Não foi possível persistir os históricos do animal %s.%n%s", animal.getIdentificador(), ex.toString()), "ERRO!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void exportarHistoricosDeUmAnimalParaTxt(Animal animal) {
        final Path pastaExportacao = Paths.get("resources/export");
        if (!pastaExportacao.toFile().exists()) {
            try {
                Files.createDirectory(pastaExportacao);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, String.format("Não foi possível criar a pasta para armazenar os arquivos exportados dos animais.%n%s", ex.toString()), "ERRO!", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        try {
            FileWriter arqTexto = new FileWriter(String.format("%s/%s.txt", pastaExportacao.toAbsolutePath(), animal.getIdentificador()));
            arqTexto.write("Animal " + animal.getIdentificador());
            for (Historico historico : HistoricosService.encontrarHistoricosDeAnimal(animal)) {
                arqTexto.write(System.lineSeparator());
                arqTexto.write(String.valueOf(historico.getPeso()) + ",");
                arqTexto.write(String.valueOf(historico.getAltura()) + ",");
                arqTexto.write(String.valueOf(historico.getTemperatura()));
            }
            arqTexto.close();
            JOptionPane.showMessageDialog(null, String.format("Históricos do animal %s exportados com sucesso!", animal.getIdentificador()), "SUCESSO!", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, String.format("Não foi possível exportar os históricos do animal %s.%n%s", animal.getIdentificador(), ex.toString()), "ERRO!", JOptionPane.ERROR_MESSAGE);
        }
    }
}
