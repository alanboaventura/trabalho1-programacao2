package view;

import controller.services.ExportarHistoricosService;
import controller.services.HistoricosService;
import model.Animal;
import model.Historico;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class InterfaceSecundaria extends JFrame {

    private JPanel contentPane;
    private JPanel tabelaBotoes;
    private JLabel lblTitulo;
    private JButton btnGerarArquivoTxt;
    private JTable tabelaGeralHistorico;
    private ScrollPane scroll;
    private JTable tabelaHistorico;

    public InterfaceSecundaria(Animal animal) {
        List<Historico> historicos = HistoricosService.encontrarHistoricosDeAnimal(animal);
        DefaultTableModel modeloTabelaHistorico = new DefaultTableModel(new String[]{"Data", "Peso", "Altura", "Temperatura"}, 0);

        for (Historico historico : historicos) {
            Date data = historico.getData();
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            modeloTabelaHistorico.addRow(new Object[]{formatter.format(data), historico.getPeso(), historico.getAltura(), historico.getTemperatura()});
        }

        exibirInterfaceSecundaria(modeloTabelaHistorico, animal);
    }

    private void exibirInterfaceSecundaria(DefaultTableModel modeloTabelaHistorico, Animal animal) {
        setBounds(100, 100, 450, 300);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 10));
        setContentPane(contentPane);
        setLocationRelativeTo(null);

        lblTitulo = new JLabel("Informações do animal com a identificação " + animal.getIdentificador());
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblTitulo, BorderLayout.NORTH);

        tabelaBotoes = new JPanel();
        contentPane.add(tabelaBotoes, BorderLayout.SOUTH);
        tabelaBotoes.setLayout(new GridLayout(0, 2, 50, 0));

        btnGerarArquivoTxt = new JButton("Exportar para arquivo .txt");
        btnGerarArquivoTxt.addActionListener(e -> {
            ExportarHistoricosService.exportarHistoricosDeUmAnimalParaTxt(animal);
        });
        tabelaBotoes.add(btnGerarArquivoTxt);

        tabelaHistorico = new JTable();
        tabelaHistorico.setEnabled(false);
        tabelaHistorico.setModel(modeloTabelaHistorico);
        
        scroll = new ScrollPane();
        scroll.add(tabelaHistorico);
        
        tabelaGeralHistorico = new JTable();
        tabelaGeralHistorico.setLayout(new BorderLayout());
        tabelaGeralHistorico.add(tabelaHistorico.getTableHeader(), BorderLayout.NORTH);
        tabelaGeralHistorico.add(scroll, BorderLayout.CENTER);
        
        contentPane.add(tabelaGeralHistorico, BorderLayout.CENTER);
    }
}
