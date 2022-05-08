package com.example.apphorasmais.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class Situacao implements Serializable {

	private int id;
	private String status;

	private List<Solicitacao> solicitacao;

	public Situacao() {
		super();
		this.solicitacao = new ArrayList<Solicitacao>();
	}

	public Situacao(int id, String status, List<Solicitacao> solicitacao) {
		super();
		this.id = id;
		this.status = status;
		this.solicitacao = solicitacao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Solicitacao> getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(List<Solicitacao> solicitacao) {
		this.solicitacao = solicitacao;
	}

	@Override
	public String toString() {
		return "Situacao{" +
				"id=" + id +
				", status='" + status + '\'' +
				", solicitacao=" + solicitacao +
				'}';
	}
}
