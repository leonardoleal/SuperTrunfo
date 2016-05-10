package repositorio;

import java.util.ArrayList;
import java.util.List;

import model.Jogador;

public class RepositorioJogadores {

	private List<Jogador> jogadores;

	public RepositorioJogadores() {
		jogadores = new ArrayList<Jogador>();
	}

	public void addJogador(Jogador jogador) {
		this.jogadores.add(jogador);
	}
}
