package br.com.farm.gui;

import br.com.farm.dao.*;
import br.com.farm.domain.*;
import br.com.farm.exceptions.MedicamentoExistenteException;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.Iterator;

public class JanelaPrincipal extends JFrame {

	private JPanel contentPane;
	private JTextField edtCodigo;
	private JTextField edtFormula;
	private JTextField edtNome;
	private JTextField edtPreco;
	private JTable table;
	private MedicamentoDAOArray medicamentos;
	JScrollPane painelRegistros;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
			
		JanelaPrincipal frame = new JanelaPrincipal();
		frame.setVisible(true);
				
		
		
	}

	/**
	 * Create the frame.
	 */
	public JanelaPrincipal() {
		this.medicamentos = new MedicamentoDAOArray();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 676, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel painelBotao = new JPanel();
		
		JPanel painelDados = new JPanel();
		
		painelRegistros = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(painelBotao, GroupLayout.DEFAULT_SIZE, 654, Short.MAX_VALUE)
						.addComponent(painelRegistros, GroupLayout.PREFERRED_SIZE, 649, GroupLayout.PREFERRED_SIZE)
						.addComponent(painelDados, GroupLayout.PREFERRED_SIZE, 654, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(painelBotao, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(painelDados, GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(painelRegistros, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
		);
		
		
		
		carregarLista();
		
		JButton btnNovo = new JButton("Novo");
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}

			
		});
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvarDados();
			}

			
		});
		
		JButton btnAlterar = new JButton("Alterar");
		
		JButton btnExcluir = new JButton("Excluir");
		GroupLayout gl_painelBotao = new GroupLayout(painelBotao);
		gl_painelBotao.setHorizontalGroup(
			gl_painelBotao.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelBotao.createSequentialGroup()
					.addComponent(btnNovo)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSalvar)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnAlterar)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnExcluir)
					.addContainerGap(358, Short.MAX_VALUE))
		);
		gl_painelBotao.setVerticalGroup(
			gl_painelBotao.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelBotao.createSequentialGroup()
					.addGroup(gl_painelBotao.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNovo)
						.addComponent(btnSalvar)
						.addComponent(btnAlterar)
						.addComponent(btnExcluir))
					.addContainerGap(37, Short.MAX_VALUE))
		);
		painelBotao.setLayout(gl_painelBotao);
		
		JLabel lblCodigo = new JLabel("C\u00F3digo:");
		
		edtCodigo = new JTextField();
		edtCodigo.setColumns(10);
		
		JLabel lblFormula = new JLabel("F\u00F3rmula:");
		
		JLabel lblNome = new JLabel("Nome:");
		
		JLabel lblPreco = new JLabel("Pre\u00E7o:");
		
		edtFormula = new JTextField();
		edtFormula.setColumns(10);
		
		edtNome = new JTextField();
		edtNome.setColumns(10);
		
		edtPreco = new JTextField();
		edtPreco.setColumns(10);
		GroupLayout gl_painelDados = new GroupLayout(painelDados);
		gl_painelDados.setHorizontalGroup(
			gl_painelDados.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelDados.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_painelDados.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_painelDados.createSequentialGroup()
							.addComponent(lblCodigo)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(edtCodigo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblNome)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(edtNome))
						.addGroup(gl_painelDados.createSequentialGroup()
							.addGroup(gl_painelDados.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblPreco)
								.addComponent(lblFormula))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_painelDados.createParallelGroup(Alignment.LEADING)
								.addComponent(edtPreco, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(edtFormula, GroupLayout.PREFERRED_SIZE, 541, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(57, Short.MAX_VALUE))
		);
		gl_painelDados.setVerticalGroup(
			gl_painelDados.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelDados.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_painelDados.createParallelGroup(Alignment.BASELINE)
						.addComponent(edtCodigo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNome)
						.addComponent(lblCodigo)
						.addComponent(edtNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_painelDados.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblFormula)
						.addComponent(edtFormula, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_painelDados.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPreco)
						.addComponent(edtPreco, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(17, Short.MAX_VALUE))
		);
		painelDados.setLayout(gl_painelDados);
		contentPane.setLayout(gl_contentPane);
	}
	
	private void limparCampos() {
		// TODO Auto-generated method stub
		this.edtCodigo.setText("");
		this.edtFormula.setText("");
		this.edtNome.setText("");
		this.edtPreco.setText("");
	}
	
	private void carregarCampos(int codigo){
		Medicamento med = medicamentos.buscar(codigo);
		this.edtCodigo.setText(med.getCodigo()+"");
		this.edtFormula.setText(med.getFormula());
		this.edtNome.setText(med.getNome());
		this.edtPreco.setText(med.getPreco()+"");
	}
	
	private void salvarDados() {
		// TODO Auto-generated method stub
		int codigo = Integer.parseInt( this.edtCodigo.getText() );
		String nome = this.edtNome.getText();
		String formula = this.edtFormula.getText();
		double preco = Double.parseDouble(this.edtPreco.getText());
		Medicamento med = new Medicamento( nome,  formula,  preco,  codigo);
		try {
			this.medicamentos.adicionar(med);
			carregarLista();
			limparCampos();
		} catch (MedicamentoExistenteException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Medicamento com código já existe" );
		}
		
	}
	
	
	private void carregarLista(){
		
		Collection<Medicamento> c = this.medicamentos.listar();
		Iterator<Medicamento> it = c.iterator();
		
		DefaultTableModel model = new DefaultTableModel(); 

		model.addColumn("Código"); 
		model.addColumn("Nome"); 
		model.addColumn("Fórmula"); 
		model.addColumn("Preço"); 
		
		while(it.hasNext()){
			Medicamento med = it.next();
			Object[] linha = new Object[4];
			linha[0] = med.getCodigo();
			linha[1] = med.getNome();
			linha[2] = med.getFormula();
			linha[3] = med.getPreco();
			model.addRow(linha);
		}
		
		table = new JTable(model){
			  public boolean isCellEditable(int rowIndex, int colIndex) {
				  return false; //Disallow the editing of any cell
			  }
		};
		
		painelRegistros.setViewportView(table);
		
		table.addMouseListener(new MouseAdapter() {
			  public void mouseClicked(MouseEvent e) {
			    if (e.getClickCount() == 2) {
			      JTable target = (JTable)e.getSource();
			      int row = target.getSelectedRow();
			      int column = target.getSelectedColumn();
			      Integer valor = (Integer) table.getModel().getValueAt(row, 0);
			      carregarCampos(valor.intValue());
			    }
			  }
			});
			
	}
	
	
	
	
}
