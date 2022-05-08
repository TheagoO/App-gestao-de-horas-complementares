package com.example.apphorasmais.model.entity;

import java.io.Serializable;

/**
 * @author Thiago Ferreira Assumpção
 */

public class AtividadeComplementar implements Serializable {

	private int id;
	
	private String titulo;
	private int cargaHoraria;
	private String instituicao;
	private int horaComplementar;

	private Atividade atividade;

	public AtividadeComplementar() {
		super();
		this.atividade = new Atividade();
	}

	public AtividadeComplementar(int id, String titulo, int cargaHoraria, String instituicao, int horaComplementar,
			Atividade atividade) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.cargaHoraria = cargaHoraria;
		this.instituicao = instituicao;
		this.horaComplementar = horaComplementar;
		this.atividade = atividade;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(int cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public String getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}

	public int getHoraComplementar() {
		return horaComplementar;
	}

	public void setHoraComplementar(int horaComplementar) {
		this.horaComplementar = horaComplementar;
	}

	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

	@Override
	public String toString() {
		return "AtividadeComplementar{" +
				"id=" + id +
				", titulo='" + titulo + '\'' +
				", cargaHoraria=" + cargaHoraria +
				", instituicao='" + instituicao + '\'' +
				", horaComplementar=" + horaComplementar +
				", atividade=" + atividade +
				'}';
	}
}
