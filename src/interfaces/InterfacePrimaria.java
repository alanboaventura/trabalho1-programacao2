package interfaces;

import java.awt.EventQueue;
import java.awt.ScrollPane;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.Animal;

public class InterfacePrimaria extends JFrame {

	private DefaultTableModel modeloTabelaInformacoesSalva;

	public DefaultTableModel getModeloTabelaInformacoesSalva() {
		return modeloTabelaInformacoesSalva;
	}

	public void setModeloTabelaInformacoesSalva(DefaultTableModel modeloTabelaInformacoesSalva) {
		this.modeloTabelaInformacoesSalva = modeloTabelaInformacoesSalva;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfacePrimaria interfacePrimaria = new InterfacePrimaria();
					interfacePrimaria.setModeloTabelaInformacoesSalva(new DefaultTableModel(0, 5));
					interfacePrimaria.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public InterfacePrimaria() {
		JPanel contentPane;
		JTable tabelaInformacoes;
		JTextField txtPesquisa;
		JButton btnPesquisar;
		JButton btnImportar;
		ScrollPane scroll;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Consulta animais");
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		tabelaInformacoes = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tabelaInformacoes.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evento) {
				if (evento.getClickCount() == 2) {
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								InterfaceSecundaria interfaceSecundaria = new InterfaceSecundaria(
										(int) tabelaInformacoes.getValueAt(tabelaInformacoes.getSelectedRow(), 0));
								interfaceSecundaria.setVisible(true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
			}
		});
		tabelaInformacoes.setModel(modeloTabelaInformacoes());
		scroll = new ScrollPane();
		scroll.setBounds(27, 50, 375, 157);
		scroll.add(tabelaInformacoes);
		contentPane.add(scroll);

		txtPesquisa = new JTextField();
		txtPesquisa.setBounds(27, 22, 255, 20);
		contentPane.add(txtPesquisa);
		txtPesquisa.setColumns(10);

		btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (txtPesquisa.getText().equals("")) {
					tabelaInformacoes.setModel(modeloTabelaInformacoes());
					tabelaInformacoes.repaint();
				} else {
					DefaultTableModel modeloTabelaInformacoes = modeloTabelaInformacoes();
					DefaultTableModel modeloTabelaPesquisa = new DefaultTableModel(0,
							modeloTabelaInformacoes.getColumnCount());

					for (int i = 0; i < modeloTabelaInformacoes.getRowCount(); i++) {
						if (txtPesquisa.getText().equals(modeloTabelaInformacoes.getValueAt(i, 0).toString())) {
							System.out.println("Entro");
							modeloTabelaPesquisa.addRow(new Object[] { modeloTabelaInformacoes.getValueAt(i, 0),
									modeloTabelaInformacoes.getValueAt(i, 1), modeloTabelaInformacoes.getValueAt(i, 2),
									modeloTabelaInformacoes.getValueAt(i, 3),
									modeloTabelaInformacoes.getValueAt(i, 4), });
						}
					}
					
					if(modeloTabelaPesquisa.getRowCount() > 0) {
						tabelaInformacoes.setModel(modeloTabelaPesquisa);
						tabelaInformacoes.repaint();
					}
					else {
						JOptionPane.showMessageDialog(null, "Não foi encontrado nenhuma informação para o dado inserido na pesquisa");
					}

				}
				txtPesquisa.setText("");
			}
		});
		btnPesquisar.setBounds(302, 22, 100, 20);
		contentPane.add(btnPesquisar);

		btnImportar = new JButton("Importar");
		btnImportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnImportar.setBounds(27, 218, 89, 23);
		contentPane.add(btnImportar);
	}

	public DefaultTableModel modeloTabelaInformacoes() {
		DefaultTableModel modeloTabelaInformacoes = new DefaultTableModel(0, 5);
		adicionarValorModeloTabelaInformacoes(modeloTabelaInformacoes);
		return modeloTabelaInformacoes;
	}

	private void adicionarValorModeloTabelaInformacoes(DefaultTableModel modeloTabelaInformacoes) {
		for (int i = 0; i < 5; i++) {
			Animal animal = new Animal(i);
			modeloTabelaInformacoes.addRow(new Object[] { animal.getIdentificador() });
		}
	}

}
