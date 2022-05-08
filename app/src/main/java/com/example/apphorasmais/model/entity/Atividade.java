package com.example.apphorasmais.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Thiago Ferreira Assumpção
 */

public class Atividade implements Serializable {

	private int id;
	private Escopo escopo;
	private int quantidade;

	private List<AtividadeComplementar> atividadesComplementares;
	private HoraComplementar horaComplementar;

	public Atividade() {
		super();
		id = 0;
		quantidade = 0;
		this.atividadesComplementares = new ArrayList<AtividadeComplementar>();
		this.horaComplementar = new HoraComplementar();
		this.escopo = new Escopo();
	}

	public Atividade(int id, Escopo escopo, int quantidade, List<AtividadeComplementar> atividadesComplementares,
			HoraComplementar horaComplementar) {
		super();
		this.id = id;
		this.escopo = escopo;
		this.quantidade = quantidade;
		this.atividadesComplementares = atividadesComplementares;
		this.horaComplementar = horaComplementar;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Escopo getEscopo() {
		return escopo;
	}

	public void setEscopo(Escopo escopo) {
		this.escopo = escopo;
	}

	public int getQuantidade() {
		quantidade = atividadesComplementares.size();
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public List<AtividadeComplementar> getAtividadesComplementares() {
		return atividadesComplementares;
	}

	public void setAtividadesComplementares(List<AtividadeComplementar> atividadesComplementares) {
		this.atividadesComplementares = atividadesComplementares;
	}

	public HoraComplementar getHorasComplementares() {
		return horaComplementar;
	}

	public void setHorasComplementares(HoraComplementar horaComplementar) {
		this.horaComplementar = horaComplementar;
	}
	
	
	
}
