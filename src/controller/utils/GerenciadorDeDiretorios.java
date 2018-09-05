package controller.utils;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Classe utilitária para gerenciamento dos diretórios que serão acessados/criados durante a execução do sistema.
 */
public class GerenciadorDeDiretorios {

    /**
     * Cria o diretório informado caso o mesmo não exista.
     *
     * @param diretorio {@link Path} que deve ser criado caso não exista ainda.
     */
    public static void criarDiretorioCasoNaoExista(Path diretorio) {
        if (!diretorio.toFile().exists()) {
            try {
                Files.createDirectory(diretorio);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, String.format("Não foi possível criar a pasta %s.%n%s", diretorio.toAbsolutePath(), ex.toString()), "ERRO!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
