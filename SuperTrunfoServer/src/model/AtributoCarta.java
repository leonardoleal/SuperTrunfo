package model;

import java.io.Serializable;

public abstract class AtributoCarta implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nome;
	private double valor;

	public AtributoCarta(String nome, double valor) {
		this.nome = nome;
		this.valor = valor;
	}

	public final String getNome() {
		return  this.nome;
	}

	public final double getValor() {
		return this.valor;
	}

	@Override
	public boolean equals(Object obj) {
		return this.nome.equals(((AtributoCarta) obj).getNome());
	}
}
