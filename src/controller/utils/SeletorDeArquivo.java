package controller.utils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Classe responsável por implementar a seleção de um arquivo para importação no sistema, onde esse arquivo conterá as informações dos animais, criando assim seus respectivos históricos.
 */
public class SeletorDeArquivo {

    /**
     * Exibe uma interface para o usuário encontrar um arquivo <code>.dat</code> referente aos históricos dos animais em qualquer diretório.
     *
     * @return Um {@link Path} representando o arquivo selecionado pelo usuário.
     */
    public static Path selecionarArquivo() {
        // Cria um filtro para que o usuário possa selecionar apenas arquivos .dat
        FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Arquivos binários para importação de informações referentes aos animais", "dat");

        // Cria o componente para seleção do arquivo.
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(fileFilter);
        fileChooser.setDialogTitle("Escolha um arquivo para importar as informações dos animais");
        fileChooser.setCurrentDirectory(Paths.get("").toAbsolutePath().toFile());

        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return Paths.get(fileChooser.getSelectedFile().getAbsolutePath());
        }

        return null;
    }
}
