package view;

import controller.database.GerenciadorDosDadosImportados;
import controller.services.AnimalService;
import controller.services.ExportarHistoricosService;
import controller.services.ImportarHistoricosService;
import controller.utils.SeletorDeArquivo;
import model.Historico;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class InterfacePrimaria extends JFrame {

    private DefaultTableModel modeloTabelaPadrao = new DefaultTableModel(new Object[][]{}, new String[]{"Identificação", "Data", "Peso", "Altura", "Temperatura"});

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                InterfacePrimaria interfacePrimaria = new InterfacePrimaria();
                interfacePrimaria.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private InterfacePrimaria() {
        JPanel contentPane;
        JPanel tabelaPesquisa;
        JPanel tabelaBotoes;
        JPanel tabelaInformacoesGeral;
        JTable tabelaInformacoes;
        JTextField txtPesquisa;
        JButton btnPesquisar;
        JButton btnImportar;
        ScrollPane scroll;
        JLabel lblTitulo;
        JLabel icone;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Trabalho 1 Programação 2 - Alan Boaventura e Gabriel Castellani");
        setBounds(100, 100, 450, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
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
                    EventQueue.invokeLater(() -> {
                        try {
                            InterfaceSecundaria interfaceSecundaria = new InterfaceSecundaria(AnimalService.encontrarAnimal((int) tabelaInformacoes.getValueAt(tabelaInformacoes.getSelectedRow(), 0)));
                            interfaceSecundaria.setVisible(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });   
        contentPane.setLayout(new BorderLayout(0, 10));
        tabelaInformacoes.setModel(modeloTabelaInformacoesPadrao());
        scroll = new ScrollPane();
        scroll.add(tabelaInformacoes.getTableHeader());
        scroll.add(tabelaInformacoes);
        
        tabelaInformacoesGeral = new JPanel();
        tabelaInformacoesGeral.setLayout(new BorderLayout(0, 0));
        tabelaInformacoesGeral.add(scroll, BorderLayout.CENTER);
        tabelaInformacoesGeral.add(tabelaInformacoes.getTableHeader(), BorderLayout.NORTH);
        
        contentPane.add(tabelaInformacoesGeral, BorderLayout.CENTER);

        tabelaPesquisa = new JPanel();
        contentPane.add(tabelaPesquisa, BorderLayout.NORTH);
        tabelaPesquisa.setLayout(new BorderLayout(10, 10));

        txtPesquisa = new JTextField();
        tabelaPesquisa.add(txtPesquisa, BorderLayout.CENTER);
        txtPesquisa.setColumns(10);

        btnPesquisar = new JButton("Pesquisar");
        tabelaPesquisa.add(btnPesquisar, BorderLayout.EAST);
        btnPesquisar.addActionListener(arg0 -> {
            if (txtPesquisa.getText().equals("")) {
                recarregarTabela(tabelaInformacoes);
            } else {
                DefaultTableModel modeloTabelaInformacoes = modeloTabelaInformacoes();
                DefaultTableModel modeloTabelaPesquisa = new DefaultTableModel(0, modeloTabelaInformacoes.getColumnCount());

                for (int i = 0; i < modeloTabelaInformacoes.getRowCount(); i++) {
                    if (txtPesquisa.getText().equals(modeloTabelaInformacoes.getValueAt(i, 0).toString())) {
                        modeloTabelaPesquisa.addRow(new Object[]{modeloTabelaInformacoes.getValueAt(i, 0),
                                modeloTabelaInformacoes.getValueAt(i, 1),
                                modeloTabelaInformacoes.getValueAt(i, 2),
                                modeloTabelaInformacoes.getValueAt(i, 3),
                                modeloTabelaInformacoes.getValueAt(i, 4)});
                    }
                }

                if (modeloTabelaPesquisa.getRowCount() > 0) {
                    tabelaInformacoes.setModel(modeloTabelaPesquisa);
                    tabelaInformacoes.repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "Não foi encontrada nenhuma informação para o identificador animal inserido na pesquisa!");
                }
            }

            txtPesquisa.setText("");
        });

        lblTitulo = new JLabel("Consulta de animais");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        tabelaPesquisa.add(lblTitulo, BorderLayout.NORTH);

        tabelaBotoes = new JPanel();
        FlowLayout flowLayout = (FlowLayout) tabelaBotoes.getLayout();
        flowLayout.setVgap(4);
        contentPane.add(tabelaBotoes, BorderLayout.SOUTH);

        btnImportar = new JButton("Importar");
        tabelaBotoes.add(btnImportar);
        
        icone = new JLabel();
        icone.setIcon(new ImageIcon("resources//icons//informacao.png"));
        
        tabelaBotoes.add(icone);
        
        JPopupMenu popupMenu = new JPopupMenu();
        addPopup(icone, popupMenu);
        
        JLabel lblTrabalho = new JLabel("Equipe do trabalho:");
        popupMenu.add(lblTrabalho);
        
        JLabel lblAlan = new JLabel("Alan Boaventura");
        popupMenu.add(lblAlan);
        
        JLabel lblGabriel = new JLabel("Gabriel Castellani de Oliveira");
        popupMenu.add(lblGabriel);
        btnImportar.addActionListener(e -> {
            Path arquivoImportado = SeletorDeArquivo.selecionarArquivo();
            if (arquivoImportado == null) {
                return;
            }

            ImportarHistoricosService.importarHistoricosDeArquivoExterno(arquivoImportado);
            ExportarHistoricosService.persistirHistoricosDosAnimais();

            recarregarTabela(tabelaInformacoes);
        });
    }

    private void recarregarTabela(JTable tabelaInformacoes) {
        int linhas = tabelaInformacoes.getRowCount();
        for (int i = 0; i < linhas; i++) {
            DefaultTableModel model = (DefaultTableModel) tabelaInformacoes.getModel();
            model.removeRow(0);
            model.fireTableDataChanged();
            tabelaInformacoes.updateUI();
        }

        tabelaInformacoes.setModel(modeloTabelaInformacoesPadrao());
        tabelaInformacoes.repaint();
    }

    private DefaultTableModel modeloTabelaInformacoesPadrao() {
        DefaultTableModel modeloTabela = modeloTabelaPadrao;
        ImportarHistoricosService.importarHistoricosImportadosPreviamente();
        adicionarValorModeloTabelaInformacoes(modeloTabela);
        return modeloTabela;
    }

    private DefaultTableModel modeloTabelaInformacoes() {
    	DefaultTableModel modeloTabelaInformacoes = new DefaultTableModel(new String[]{"Identificação", "Data", "Peso", "Altura", "Temperatura"}, 0);
        adicionarValorModeloTabelaInformacoes(modeloTabelaInformacoes);
        return modeloTabelaInformacoes;
    }

    private void adicionarValorModeloTabelaInformacoes(DefaultTableModel modeloTabelaInformacoes) {
        List<Historico> historicos = GerenciadorDosDadosImportados.getHistoricos();
        StringBuilder historicosAdicionados = new StringBuilder(",");
        for (Historico historico : historicos) {
            if (!historicosAdicionados.toString().contains(historico.getAnimal().getIdentificador() + "")) {
                Date data = historico.getData();
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                modeloTabelaInformacoes.addRow(new Object[]{historico.getAnimal().getIdentificador(), formatter.format(data), historico.getPeso(), historico.getAltura(), historico.getTemperatura()});
                historicosAdicionados.append(historico.getAnimal().getIdentificador()).append(",");
            }
        }
    }
    
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
				popup.setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				popup.setVisible(false);
			}
		});
	}
}
