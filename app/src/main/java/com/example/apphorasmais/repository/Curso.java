package com.example.apphorasmais.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class Curso implements Serializable {

	private int id;
	private String titulo;

	private Coordenador coordenador;
	private List<Turma> turmas;

	public Curso() {
		super();
		this.coordenador = new Coordenador();
		this.turmas = new ArrayList<Turma>();
	}

	public Curso(int id, String titulo, Coordenador coordenador) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.coordenador = coordenador;
	}

	public Curso(int id, String titulo, Coordenador coordenador, List<Turma> turmas) {
		this.id = id;
		this.titulo = titulo;
		this.coordenador = coordenador;
		this.turmas = turmas;
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

	public Coordenador getCoordenador() {
		return coordenador;
	}

	public void setCoordenador(Coordenador coordenador) {
		this.coordenador = coordenador;
	}

	public List<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}

	@Override
	public String toString() {
		return "Curso{" +
				"id=" + id +
				", titulo='" + titulo + '\'' +
				", coordenador=" + coordenador +
				", turmas=" + turmas +
				'}';
	}
}
