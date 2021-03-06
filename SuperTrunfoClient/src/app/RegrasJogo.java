package app;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Queue;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.interfaces.ISuperTrunfo;
import model.AtributoCarta;
import model.Carta;
import model.Jogador;

public final class RegrasJogo {

	public static Jogador JOGADOR;
	public static Jogador ADVERSARIO;

	public RegrasJogo(Jogador jogador, Jogador adversario) {
		JOGADOR = jogador;
		ADVERSARIO = adversario;
	}

	private static boolean compararAtributo(AtributoCarta atributo, AtributoCarta atributoAdversario) {

		System.out.println("Atributo comparado: "+ atributo.getNome());

		return atributo.getValor() > atributoAdversario.getValor();
	}

	public static boolean perdeu() {
		if (JOGADOR.deckIsEmpty()) {
			System.out.println("Você PERDEU!!!.");
		}
		return JOGADOR.deckIsEmpty();
	}

	public static Carta compararCarta(Carta cartaAdversario, AtributoCarta atributo) {
		Carta cartaEmJogo = JOGADOR.removeCartaTopoDeck();

		System.out.println("\nCarta do adversário:");
		printDetalhesCarta(cartaAdversario);

		if (compararAtributo(
				cartaEmJogo.getAtributo(atributo)
				, cartaAdversario.getAtributo(atributo)
		)){
			JOGADOR.retornaCartaParaDeck(cartaEmJogo);
			JOGADOR.retornaCartaParaDeck(cartaAdversario);
			System.out.println("Você GANHOU a rodada!");
		} else {
			// perdeu descarta...
			System.out.println("Você PERDEU a rodada!");
		}

		return cartaEmJogo;
	}

	public static void turno() {
		Socket socket = null;
		Socket socketToServer;
		Scanner scanner = new Scanner(System.in);
		Carta cartaEmJogo = JOGADOR.getCartaTopoDeck();
		Carta cartaAdversario;

		try {
			socketToServer = new Socket("localhost", ADVERSARIO.getPorta());
			ObjectOutputStream outputStream = new ObjectOutputStream(socketToServer.getOutputStream());
			ObjectInputStream inputStream = new ObjectInputStream(socketToServer.getInputStream());

			//verifica se o vc perdeu
			outputStream.writeObject("adversarioPerdeu");
			if ((boolean) inputStream.readObject()) {
				System.out.println("Você GANHOU!!!.");
				System.exit(0);
			}

			printDetalhesCarta(cartaEmJogo);

			System.out.println("Escolha um atributo para comparar:");
			int iAtributo = scanner.nextInt();

			AtributoCarta atributoEscolhido = cartaEmJogo.getListaAtributos().get(iAtributo);

			HashMap<Carta, AtributoCarta> mapa = new HashMap<Carta, AtributoCarta>();
			mapa.put(cartaEmJogo, atributoEscolhido);
			outputStream.writeObject(mapa);

			cartaAdversario = (Carta) inputStream.readObject();
			compararCarta(cartaAdversario, atributoEscolhido);

			RegrasJogo.turnoAdversario();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void turnoAdversario() {
		printDetalhesCarta(JOGADOR.getCartaTopoDeck());

		System.out.println("É a vez do adversario.");
		System.out.println("Aguarde...");
	}

	private static void printDetalhesCarta(Carta carta) {
		System.out.println("\nCarta: "+ carta.getNome());

		for (AtributoCarta atributo : carta.getListaAtributos()) {
			System.out.println(
					"("+ carta.getListaAtributos().indexOf(atributo) +") "
					+ atributo.getNome() +": "
					+ atributo.getValor()
			);
		}
	}

	public static Jogador getAdversario(Jogador jogador) {
		try {
			Jogador adversario = null;
			ISuperTrunfo rSuperTrunfo = (ISuperTrunfo) Naming.lookup("rmi://localhost:1099/app/SuperTrunfo");

			System.out.println("Aguardando na fila...");

			do {
				adversario = rSuperTrunfo.buscarJogador(jogador);

				// aguarda 0.5s para verificar a fila
				try { Thread.sleep(500);
				} catch (InterruptedException e) {}
			} while(adversario == null);

			return adversario;
		} catch (RemoteException ex) {
			Logger.getLogger(RegrasJogo.class.getName()).log(Level.SEVERE, null, ex);
		} catch (NotBoundException ex) {
			Logger.getLogger(RegrasJogo.class.getName()).log(Level.SEVERE, null, ex);
		} catch (MalformedURLException ex) {
			Logger.getLogger(RegrasJogo.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	public static Queue<Carta> getDeck() {
		try {
			ISuperTrunfo rSuperTrunfo = (ISuperTrunfo) Naming.lookup("rmi://localhost:1099/app/SuperTrunfo");

			System.out.println("Aguardando na fila...");

			return rSuperTrunfo.distribuirCartas();

		} catch (RemoteException ex) {
			Logger.getLogger(RegrasJogo.class.getName()).log(Level.SEVERE, null, ex);
		} catch (NotBoundException ex) {
			Logger.getLogger(RegrasJogo.class.getName()).log(Level.SEVERE, null, ex);
		} catch (MalformedURLException ex) {
			Logger.getLogger(RegrasJogo.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
}
