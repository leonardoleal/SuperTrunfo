package model;

public abstract class AtributoCarta implements Cloneable {

	private String nome;
	private int valor;

	public AtributoCarta(String nome, int valor) {
		this.nome = nome;
		this.valor = valor;
	}

	public final String getNome() {
		return  this.nome;
	}

	public final int getValor() {
		return this.valor;
	}

	@Override
	public boolean equals(Object obj) {
		return this.nome.equals(((AtributoCarta) obj).getNome());
	}
}
