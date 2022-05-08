package com.example.apphorasmais.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class HoraComplementar implements Serializable {

	private int id;
	private int total;
	private int totalAtividade;

	private List<Atividade> atividades;

	public HoraComplementar() {
		super();
		this.id =0;
		this.atividades = new ArrayList<Atividade>();
	}

	public HoraComplementar(int id, int total, int totalAtividade, List<Atividade> atividades) {
		super();
		this.id = id;
		this.total = total;
		this.totalAtividade = totalAtividade;
		this.atividades = atividades;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTotal() {
		total = 0;
		for(Atividade a : atividades){
			for(AtividadeComplementar ac : a.getAtividadesComplementares()){
				total += ac.getHoraComplementar();
			}
		}
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getTotalAtividade() {
		totalAtividade = 0;
		for(Atividade a : atividades){
			totalAtividade = totalAtividade + a.getQuantidade();
		}
		return totalAtividade;
	}

	public void setTotalAtividade(int totalAtividade) {
		this.totalAtividade = totalAtividade;
	}

	public List<Atividade> getAtividades() {
		return atividades;
	}

	public void setAtividades(List<Atividade> atividades) {
		this.atividades = atividades;
	}

	@Override
	public String toString() {
		return "HorasComplementares{" +
				"id=" + id +
				", total=" + total +
				", totalAtividade=" + totalAtividade +
				", atividades=" + atividades +
				'}';
	}
}
