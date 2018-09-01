package controller.utils;

import controller.services.AnimalService;
import controller.GerenciadorDosDadosImportados;
import model.Animal;
import model.Historico;

import javax.swing.*;
import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Classe responsável por implementar métodos para a importação de históricos dos animais.
 */
public class ImportarHistoricos {

    /**
     * Importa os históricos dos animais de um deterinado arquivo binário.
     *
     * @param pathDoArquivo {@link Path} do arquivo binário que armazena as informações que devem ser importadas para o sistema.
     */
    public static void importarHistoricosDeArquivoExterno(Path pathDoArquivo) {
        try (DataInputStream file = new DataInputStream(new FileInputStream(pathDoArquivo.toFile()))) {
            while (true) {
                file.skipBytes(2);
                StringBuilder dataString = new StringBuilder();
                for (int aux = 0; aux <= 9; aux++) {
                    dataString.append((char) file.read());
                }
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date dataDosHistoricos = formatter.parse(dataString.toString());

                try {
                    while (true) {
                        int identificadorAnimal = file.readInt();
                        double pesoAnimal = file.readDouble();
                        short alturaAnimal = file.readShort();
                        double temperaturaAnimal = file.readDouble();

                        Animal animal = AnimalService.encontrarAnimal(identificadorAnimal);
                        Historico historico = new Historico(animal, dataDosHistoricos, pesoAnimal, alturaAnimal, temperaturaAnimal);
                        GerenciadorDosDadosImportados.adicionarHistorico(historico);
                    }

                } catch (EOFException ex) {
                    break;
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, String.format("Não foi possível importar o histórico \"%s\".%n%s", pathDoArquivo.getFileName(), ex.toString()), "ERRO!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void importarHistoricosImportadosPreviamente() {
        final Path pastaHistoricos = Paths.get("resources/historicos");
        if (!pastaHistoricos.toFile().exists()) {
            try {
                Files.createDirectory(pastaHistoricos);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, String.format("Não foi possível criar a pasta para armazenar os históricos dos animais.%n%s", ex.toString()), "ERRO!", JOptionPane.ERROR_MESSAGE);
            }
        }

        List<Integer> identificadoresDosAnimais = new ArrayList<>();
        try {
            DirectoryStream<Path> stream = Files.newDirectoryStream(pastaHistoricos);
            for (Path p : stream) {
                identificadoresDosAnimais.add(Integer.parseInt(p.getFileName().toString().split(".dat")[0]));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, String.format("Não foi possível importar os animais.%n%s", ex.toString()), "ERRO!", JOptionPane.ERROR_MESSAGE);
        }

        for (Integer identificador : identificadoresDosAnimais){
            Animal animal = AnimalService.encontrarAnimal(identificador);

            try (ObjectInputStream file = new ObjectInputStream(new FileInputStream(Paths.get(String.format("%s/%s.dat", pastaHistoricos.toAbsolutePath(), animal.getIdentificador())).toFile()))) {
                List<Historico> historicos = (List<Historico>) file.readObject();
                historicos.forEach(GerenciadorDosDadosImportados::adicionarHistorico);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, String.format("Não foi possível importar o histórico do animal %s.%n%s", animal.getIdentificador(), ex.toString()), "ERRO!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
