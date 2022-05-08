package com.example.apphorasmais.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class Escopo implements Serializable {

	private int id;
	private String atividade;

	private List<Requerimento> requerimento;
	private List<Atividade> atividades;

	public Escopo() {
		super();
		this.requerimento = new ArrayList<Requerimento>();
		this.atividades = new ArrayList<Atividade>();
	}

	public Escopo(int id, String atividade, List<Requerimento> requerimento) {
		super();
		this.id = id;
		this.atividade = atividade;
		this.requerimento = requerimento;
	}

	public Escopo(int id, String atividade, List<Requerimento> requerimento, List<Atividade> atividades) {
		this.id = id;
		this.atividade = atividade;
		this.requerimento = requerimento;
		this.atividades = atividades;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAtividade() {
		return atividade;
	}

	public void setAtividade(String atividade) {
		this.atividade = atividade;
	}

	public List<Requerimento> getRequerimento() {
		return requerimento;
	}

	public void setRequerimento(List<Requerimento> requerimento) {
		this.requerimento = requerimento;
	}

	public List<Atividade> getAtividades() {
		return atividades;
	}

	public void setAtividades(List<Atividade> atividades) {
		this.atividades = atividades;
	}

	@Override
	public String toString() {
		return "Escopo{" +
				"id=" + id +
				", atividade='" + atividade + '\'' +
				", requerimento=" + requerimento +
				'}';
	}
}
