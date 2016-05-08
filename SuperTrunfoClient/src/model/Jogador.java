package model;

import java.util.List;
import java.util.Stack;

public class Jogador {

	private String nome;
	private Stack<Carta> deck;

	public Jogador(String nome) {
		// TODO Auto-generated constructor stub
	}

	public String getNome() {
		return this.nome;
	}

	public void setDeck(List<Carta> cartas) {
		this.deck = (Stack<Carta>) cartas;
	}

	public Carta getCartaTopoDeck() {
		return deck.pop();
	}

	public void retornaCartaDeck(Carta carta) {
		this.deck.add(carta);
	}
}
