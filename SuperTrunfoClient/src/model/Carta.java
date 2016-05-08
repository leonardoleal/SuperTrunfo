package model;

import java.util.List;

public class Carta {

	private String nome;
	private List<AtributoCarta> atributos;

	public Carta(String nome, List<AtributoCarta> atributos) {
		this.nome = nome;
		this.atributos = atributos;
	}

	public String getNome() {
		return this.nome;
	}

	public List<AtributoCarta> getListaAtributos() {
		return this.atributos;
	}

	public AtributoCarta getAtributo(AtributoCarta atributo) {
		int indice = this.atributos.indexOf(atributo);

		if (indice < 0) {
			return null;
		}

		return this.atributos.get(indice);
	}
}
