package app;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import app.interfaces.ISuperTrunfo;
import model.Carta;
import model.Jogador;
import repositorio.RepositorioCartas;

public class SuperTrunfo extends UnicastRemoteObject implements ISuperTrunfo {
	private static final long serialVersionUID = 1L;

	private static Stack<Carta> CARTAS = new Stack<Carta>();
	private static Queue<Jogador> JOGADORES = new LinkedList<Jogador>();

	RepositorioCartas repoCartas;

	public SuperTrunfo() throws RemoteException {
		super();
		repoCartas = new RepositorioCartas();
	}

	@Override
	public synchronized Queue<Carta> distribuirCartas() throws RemoteException {
		Queue<Carta> retorno = new LinkedList<Carta>();
		int arrSize = CARTAS.size();

		if (CARTAS.isEmpty()) {
			CARTAS.addAll(repoCartas.getTodasCartas());
			arrSize = CARTAS.size()/2;
		}

		java.util.Collections.shuffle(CARTAS);

		for (int i = 0; i < arrSize; i++) {
			retorno.add(CARTAS.pop());
		}

		return retorno;
	}

	@Override
	public synchronized Jogador buscarJogador(Jogador jogador) throws RemoteException{
		if (! JOGADORES.contains(jogador)) {
			JOGADORES.add(jogador);
		}

		System.out.println("jogadores nafila: " + JOGADORES.size());

		if (!JOGADORES.peek().equals(jogador)) {
			return JOGADORES.poll();
		} else {
			JOGADORES.peek().setDesafiado(true);
			return null;
		}
	}
}
