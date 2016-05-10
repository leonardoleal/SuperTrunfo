package app.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Queue;

import model.Carta;
import model.Jogador;

public interface ISuperTrunfo extends Remote {

	public Queue<Carta> distribuirCartas() throws RemoteException;

	public Jogador buscarJogador(Jogador jogador) throws RemoteException;
}
