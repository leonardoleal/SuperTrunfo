package model;

import java.io.Serializable;
import java.util.Queue;

public class Jogador implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nome;
	private Queue<Carta> deck;
	private String host;
	private boolean desafiado;

	public Jogador(String nome, String host) {
		this.nome = nome;
		this.host = host;
	}

	public String getNome() {
		return this.nome;
	}

	public void setDeck(Queue<Carta> queue) {
		this.deck = queue;
	}

	public Carta getCartaTopoDeck() {
		return deck.remove();
	}

	public void retornaCartaParaDeck(Carta carta) {
		this.deck.add(carta);
	}

	public String getHost() {
		return host;
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
}
