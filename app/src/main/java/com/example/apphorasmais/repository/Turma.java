package com.example.apphorasmais.repository;

import java.io.Serializable;
import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class Turma implements Serializable {

	private int id;
	private String grupo;

	private Curso curso;

	private PeriodoLetivo periodo;

	private List<Aluno> aluno;

	public Turma() {
		super();
		curso = new Curso();
		periodo = new PeriodoLetivo();
	}

	public Turma(int id, String grupo) {
		this.id = id;
		this.grupo = grupo;
		curso = new Curso();
		periodo = new PeriodoLetivo();
	}

	public Turma(int id, String grupo, Curso curso, PeriodoLetivo periodo, List<Aluno> aluno) {
		super();
		this.id = id;
		this.grupo = grupo;
		this.curso = curso;
		this.periodo = periodo;
		this.aluno = aluno;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public PeriodoLetivo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(PeriodoLetivo periodo) {
		this.periodo = periodo;
	}

	public List<Aluno> getAluno() {
		return aluno;
	}

	public void setAluno(List<Aluno> aluno) {
		this.aluno = aluno;
	}

	@Override
	public String toString() {
		return "Turma{" +
				"id=" + id +
				", grupo=" + grupo +
				", curso=" + curso +
				", periodo=" + periodo +
				", aluno=" + aluno +
				'}';
	}
}
