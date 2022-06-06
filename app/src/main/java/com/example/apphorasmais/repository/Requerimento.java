package com.example.apphorasmais.repository;

import java.io.Serializable;

/**
 * @author Thiago Ferreira Assumpção
 */

public class Requerimento implements Serializable {

	private int id;
	private String titulo;
	private int cargaHoraria;
	private String dataInicio;
	private String dataTermino;
	private String instituicao;

	private Escopo escopo;

	public Requerimento() {
		super();
		this.escopo = new Escopo();
	}

	public Requerimento(int id, String titulo, int cargaHoraria, String dataInicio, String dataTermino,
			String instituicao, Escopo escopo) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.cargaHoraria = cargaHoraria;
		this.dataInicio = dataInicio;
		this.dataTermino = dataTermino;
		this.instituicao = instituicao;
		this.escopo = escopo;
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

	public String getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}

	public String getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(String dataTermino) {
		this.dataTermino = dataTermino;
	}

	public String getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}

	public Escopo getEscopo() {
		return escopo;
	}

	public void setEscopo(Escopo escopo) {
		this.escopo = escopo;
	}

	@Override
	public String toString() {
		return "Requerimento{" +
				"id=" + id +
				", titulo='" + titulo + '\'' +
				", cargaHoraria=" + cargaHoraria +
				", dataInicio='" + dataInicio + '\'' +
				", dataTermino='" + dataTermino + '\'' +
				", instituicao='" + instituicao + '\'' +
				", escopo=" + escopo +
				'}';
	}
}
