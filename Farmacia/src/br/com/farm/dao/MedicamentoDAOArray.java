package br.com.farm.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;



import br.com.farm.domain.Medicamento;
import br.com.farm.exceptions.MedicamentoExistenteException;

public class MedicamentoDAOArray {
	private ArrayList<Medicamento> colecao;
	
	public MedicamentoDAOArray(){
		colecao = new ArrayList<Medicamento>();
	}
	
	public void adicionar(Medicamento med) throws MedicamentoExistenteException{
		Medicamento m = this.buscar(med.getCodigo());
		if( m != null){
			throw new MedicamentoExistenteException();
		}
		this.colecao.add(med);
	}
	
	public Medicamento buscar(int codigo){
		Medicamento retorno = null;
		Iterator<Medicamento> it = this.colecao.iterator();
		while(it.hasNext()){
			Medicamento med = it.next();
			if( med.getCodigo() == codigo){
				retorno = med;
			}
		}
		return retorno;
	}
	
	public Collection<Medicamento> listar(){
		return this.colecao;
	}
	
	public void remover(int codigo){
		Medicamento med = this.buscar(codigo);
		if( med != null){
			this.colecao.remove(med);
		}
	}
	
	
}
