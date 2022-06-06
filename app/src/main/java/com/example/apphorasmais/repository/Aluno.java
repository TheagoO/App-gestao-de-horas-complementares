package com.example.apphorasmais.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class Aluno implements Serializable {

	private int id;
	private String nome;
	private String usuario;
	private String senha;

	private Turma turma;
	private HoraComplementar horasComplementares;
	private List<Solicitacao> solicitacao;
	
	
	
	public Aluno() {
		super();
		this.id = 0;
		this.turma = new Turma();
		this.horasComplementares = new HoraComplementar();
		this.solicitacao = new ArrayList<Solicitacao>();
	}

	public Aluno(int id, String nome, String usuario, String senha) {
		this.id = id;
		this.nome = nome;
		this.usuario = usuario;
		this.senha = senha;
		this.turma = new Turma();
		this.horasComplementares = new HoraComplementar();
		this.solicitacao = new ArrayList<Solicitacao>();
	}

	public Aluno(String nome, String usuario) {
		this.id=0;
		this.nome = nome;
		this.usuario = usuario;
		this.turma = new Turma();
		this.horasComplementares = new HoraComplementar();
		this.solicitacao = new ArrayList<Solicitacao>();
	}

	public Aluno(int id, String nome, String usuario, String senha, Turma turma,
				 HoraComplementar horasComplementares, List<Solicitacao> solicitacao) {
		super();
		this.id = id;
		this.nome = nome;
		this.usuario = usuario;
		this.senha = senha;
		this.turma = turma;
		this.horasComplementares = horasComplementares;
		this.solicitacao = solicitacao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public HoraComplementar getHorasComplementares() {
		return horasComplementares;
	}

	public void setHorasComplementares(HoraComplementar horasComplementares) {
		this.horasComplementares = horasComplementares;
	}

	public List<Solicitacao> getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(List<Solicitacao> solicitacao) {
		this.solicitacao = solicitacao;
	}
	
	
	
}
