package model;

import java.io.Serializable;
import java.util.Queue;

public class Jogador implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nome;
	private Queue<Carta> deck;
	private String host;
	private int porta;
	private boolean desafiado;

	public Jogador(String nome, String host, int porta) {
		this.nome = nome;
		this.host = host;
		this.porta = porta;
	}

	public String getNome() {
		return this.nome;
	}

	public void setDeck(Queue<Carta> queue) {
		this.deck = queue;
	}

	public Carta getCartaTopoDeck() {
		return deck.peek();
	}

	public Carta removeCartaTopoDeck() {
		return deck.remove();
	}

	public void retornaCartaParaDeck(Carta carta) {
		this.deck.add(carta);
	}

	public String getHost() {
		return host;
	}

	public int getPorta() {
		return porta;
	}

	public boolean isDesafiado() {
		return desafiado;
	}

	public void setDesafiado(boolean desafiado) {
		this.desafiado = desafiado;
	}

	@Override
	public boolean equals(Object obj) {
		return this.nome.equals(((Jogador) obj).getNome());
	}

	public boolean deckIsEmpty() {
		return this.deck.isEmpty();
	}
}
