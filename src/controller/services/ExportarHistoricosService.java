package controller.services;

import controller.database.GerenciadorDosDadosImportados;
import controller.utils.GerenciadorDeDiretorios;
import model.Animal;
import model.Historico;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Classe responsável por implementar métodos para a exportação de históricos dos animais.
 */
public class ExportarHistoricosService {

    /**
     * Persiste os hitóricos dos animais importados em um arquivo binário para que funcione como a base de dados do sistema.
     */
    public static void persistirHistoricosDosAnimais() {
        final List<Animal> animais = GerenciadorDosDadosImportados.getAnimais();
        final List<Historico> historicos = GerenciadorDosDadosImportados.getHistoricos();
        if (animais == null || animais.isEmpty() || historicos == null || historicos.isEmpty()) return;

        for (Animal animal : animais) {
            List<Historico> historicosDoAnimal = HistoricosService.encontrarHistoricosDeAnimal(animal);

            Path pastaDados = Paths.get("resources/database");
            GerenciadorDeDiretorios.criarDiretorioCasoNaoExista(pastaDados);
            try (ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(Paths.get(String.format("%s/%s.dat", pastaDados.toAbsolutePath(), animal.getIdentificador())).toFile()))) {
                file.reset();
                file.writeObject(historicosDoAnimal);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, String.format("Não foi possível persistir os históricos do animal %s.%n%s", animal.getIdentificador(), ex.toString()), "ERRO!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Exporta todos os históricos de um determinado animal para um arquivo txt, que pode ser encontrado na pasta "exports".
     *
     * @param animal {@link Animal} que deve ter seus históricos exportados em um arquivo txt.
     */
    public static void exportarHistoricosDeUmAnimalParaTxt(Animal animal) {
        Path pastaExportacao = Paths.get("resources/export");
        GerenciadorDeDiretorios.criarDiretorioCasoNaoExista(pastaExportacao);

        try {
            FileWriter arqTexto = new FileWriter(String.format("%s/%s.txt", pastaExportacao.toAbsolutePath(), animal.getIdentificador()));
            arqTexto.write("Animal " + animal.getIdentificador());
            DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

            for (Historico historico : HistoricosService.encontrarHistoricosDeAnimal(animal)) {
                arqTexto.write(System.lineSeparator());
                arqTexto.write(System.lineSeparator());
                arqTexto.write("Data: " + dateFormatter.format(historico.getData()));
                arqTexto.write(System.lineSeparator());
                arqTexto.write("Peso: " + String.valueOf(historico.getPeso()));
                arqTexto.write(System.lineSeparator());
                arqTexto.write("Altura: " + String.valueOf(historico.getAltura()));
                arqTexto.write(System.lineSeparator());
                arqTexto.write("Temperatura: " + String.valueOf(historico.getTemperatura()));
            }
            arqTexto.close();
            JOptionPane.showMessageDialog(null, String.format("Históricos do animal %s exportados com sucesso!", animal.getIdentificador()), "SUCESSO!", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, String.format("Não foi possível exportar os históricos do animal %s.%n%s", animal.getIdentificador(), ex.toString()), "ERRO!", JOptionPane.ERROR_MESSAGE);
        }
    }
}
