package view;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import controller.services.HistoricosService;
import java.util.Date;
import java.util.List;
import model.Animal;
import model.Historico;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JButton;

public class InterfaceSecundaria extends JFrame {

	private JPanel contentPane;
	private JPanel tabelaBotoes;
	private JLabel lblTitulo;
	private JButton btnGerarArquivoTxt;
	private ScrollPane scroll;
	private JTable tabelaHistorico;

//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					InterfaceSecundaria frame = new InterfaceSecundaria();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	public InterfaceSecundaria(Animal animal) {
		List<Historico> historicos = HistoricosService.encontrarHistoricosDeAnimal(animal);
		DefaultTableModel modeloTabelaHistorico = new DefaultTableModel(0, 4);
		modeloTabelaHistorico.addRow(new Object[] { "Data", "Peso", "Altura", "Temperatura" });

		for (Historico historico : historicos) {
			Date data = historico.getData();
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

			modeloTabelaHistorico.addRow(new Object[] { formatter.format(data), historico.getPeso(),
					historico.getAltura(), historico.getTemperatura() });
		}
		exibirInterfaceSecundaria(modeloTabelaHistorico, animal.getIdentificador());
	}

	public void exibirInterfaceSecundaria(DefaultTableModel modeloTabelaHistorico, int identificacao) {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 10));
		setContentPane(contentPane);
		setLocationRelativeTo(null);

		lblTitulo = new JLabel("Informações do animal com a identificação " + identificacao);
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblTitulo, BorderLayout.NORTH);

		tabelaBotoes = new JPanel();
		contentPane.add(tabelaBotoes, BorderLayout.SOUTH);
		tabelaBotoes.setLayout(new GridLayout(0, 2, 50, 0));

		btnGerarArquivoTxt = new JButton("Gerar arquivo txt");
		tabelaBotoes.add(btnGerarArquivoTxt);

		tabelaHistorico = new JTable();
		tabelaHistorico.setEnabled(false);
		tabelaHistorico.setModel(modeloTabelaHistorico);

		scroll = new ScrollPane();
		scroll.add(tabelaHistorico);

		contentPane.add(scroll, BorderLayout.CENTER);
	}
}
