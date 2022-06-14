package com.example.apphorasmais.model.entity;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author Thiago Ferreira Assumpção
 */

public class Solicitacao implements Serializable {

	private int id;
	private int protocolo;
	private LocalDate dataSolicitacao;

	private Aluno aluno;
	private Situacao situacao;
	private Requerimento requerimento;

	public Solicitacao() {
		super();
		this.dataSolicitacao = LocalDate.now();
		this.aluno = new Aluno();
		this.situacao = new Situacao();
		this.requerimento = new Requerimento();
	}

	public Solicitacao(int id, int protocolo, LocalDate dataSolicitacao, Aluno aluno, Situacao situacao,
			Requerimento requerimento) {
		super();
		this.id = id;
		this.protocolo = protocolo;
		this.dataSolicitacao = dataSolicitacao;
		this.aluno = aluno;
		this.situacao = situacao;
		this.requerimento = requerimento;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(int protocolo) {
		this.protocolo = protocolo;
	}

	public LocalDate getDataSolicitacao() {
		return dataSolicitacao;
	}

	public void setDataSolicitacao(LocalDate dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public Requerimento getRequerimento() {
		return requerimento;
	}

	public void setRequerimento(Requerimento requerimento) {
		this.requerimento = requerimento;
	}
	
	
}
