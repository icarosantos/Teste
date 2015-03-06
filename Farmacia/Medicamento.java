package br.com.farm.domain;

public class Medicamento {
	private String nome;
	private String formula;
	private double preco;
	private int codigo;
	public Medicamento(String nome, String formula, double preco, int codigo) {
		super();
		this.nome = nome;
		this.formula = formula;
		this.preco = preco;
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(double preco) {
		this.preco = preco;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
}
