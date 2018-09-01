package controller.utils;

import controller.GerenciadorDosDadosImportados;
import controller.services.HistoricosService;
import model.Animal;
import model.Historico;

import javax.swing.*;
import java.io.FileOutputStream;
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

            final Path pastaHistoricos = Paths.get("resources/historicos");
            if (!pastaHistoricos.toFile().exists()) {
                try {
                    Files.createDirectory(pastaHistoricos);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, String.format("Não foi possível criar a pasta para armazenar os históricos dos animais.%n%s", ex.toString()), "ERRO!", JOptionPane.ERROR_MESSAGE);
                }
            }
            try (ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(Paths.get(String.format("%s/%s.dat", pastaHistoricos.toAbsolutePath(), animal.getIdentificador())).toFile()))) {
                file.reset();
                file.writeObject(historicosDoAnimal);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, String.format("Não foi possível exportar os históricos do animal %s.%n%s", animal.getIdentificador(), ex.toString()), "ERRO!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
