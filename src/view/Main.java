package view;

import controller.utils.ExportarHistoricos;
import controller.GerenciadorDosDadosImportados;
import controller.utils.ImportarHistoricos;
import controller.utils.SeletorDeArquivo;
import model.Historico;

import javax.swing.*;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Testes - Deve ser apagado / Substituido pelo frontend.
 */
public class Main {

    public static void main(String args[]) {
        ImportarHistoricos.importarHistoricosImportadosPreviamente();
        exibeHistoricosImportados();

        for (int x = 0; x < 2; x++) {
            Path arquivoImportado = SeletorDeArquivo.selecionarArquivo();
            if (arquivoImportado == null) return;

            ImportarHistoricos.importarHistoricosDeArquivoExterno(arquivoImportado);
            ExportarHistoricos.persistirHistoricosDosAnimais();

            exibeHistoricosImportados();
        }
    }

    private static void exibeHistoricosImportados() {
        List<Historico> historicos = GerenciadorDosDadosImportados.getHistoricos();
        JOptionPane.showMessageDialog(null, "Quantidade de arquivos importados " + historicos.size());
        for (Historico historico : historicos) {
            Date data = historico.getData();
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\n");
            stringBuilder.append("Data: ").append(formatter.format(data));
            stringBuilder.append("\n");
            stringBuilder.append("Animal: ").append(historico.getAnimal().getIdentificador());
            stringBuilder.append("\n");
            stringBuilder.append("Peso: ").append(historico.getPeso());
            stringBuilder.append("\n");
            stringBuilder.append("Altura: ").append(historico.getAltura());
            stringBuilder.append("\n");
            stringBuilder.append("Temperatura: ").append(historico.getTemperatura());
            stringBuilder.append("\n");

            JOptionPane.showMessageDialog(null, stringBuilder.toString());
        }
    }
}
