package com.example.apphorasmais.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class PeriodoLetivo implements Serializable {

	private int id;
	private String periodo;

	private List<Turma> turma;

	public PeriodoLetivo() {
		super();
		this.turma = new ArrayList<Turma>();
	}

	public PeriodoLetivo(int id, String periodo, List<Turma> turma) {
		super();
		this.id = id;
		this.periodo = periodo;
		this.turma = turma;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public List<Turma> getTurma() {
		return turma;
	}

	public void setTurma(List<Turma> turma) {
		this.turma = turma;
	}

	@Override
	public String toString() {
		return "PeriodoLetivo{" +
				"id=" + id +
				", periodo='" + periodo + '\'' +
				", turma=" + turma +
				'}';
	}
}
