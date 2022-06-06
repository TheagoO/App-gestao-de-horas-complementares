package com.example.apphorasmais.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class Coordenador implements Serializable {

	private int id;
	private String nome;
	private String usuario;
	private String senha;

	private List<Curso> curso;

	public Coordenador() {
		super();
		this.curso = new ArrayList<Curso>();
	}

	public Coordenador(int id, String nome, String usuario, String senha) {
		this.id = id;
		this.nome = nome;
		this.usuario = usuario;
		this.senha = senha;
		this.curso = new ArrayList<Curso>();
	}

	public Coordenador(String nome, String usuario) {
		this.id = 0;
		this.nome = nome;
		this.usuario = usuario;
		this.curso = new ArrayList<Curso>();
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Curso> getCurso() {
		return curso;
	}

	public void setCurso(List<Curso> curso) {
		this.curso = curso;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Coordenador{" +
				"id=" + id +
				", nome='" + nome + '\'' +
				", usuario='" + usuario + '\'' +
				", senha='" + senha + '\'' +
				", curso=" + curso +
				'}';
	}
}
