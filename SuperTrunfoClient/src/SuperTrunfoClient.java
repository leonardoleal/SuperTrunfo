

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.RegrasJogo;
import app.SuperTrunfoListener;
import app.interfaces.ISuperTrunfo;
import model.Jogador;

public class SuperTrunfoClient {
	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws UnknownHostException {
		String host = InetAddress.getLocalHost().toString();
		int porta = portaAleatoria();
		Jogador jogador, adversario = null;

		System.out.println("Digite seu nome: ");
//		jogador = new Jogador(scanner.nextLine(), host +":"+ porta);
		jogador = new Jogador(scanner.nextLine(), host, porta);

		// comunica com o servidor
		System.out.println("Iniciando conexão com o servidor...");
		adversario = solicitarInfoServidor(jogador);
		System.out.println("Seu adversário é:"+ adversario.getNome());

		// inicia jogo
		iniciaJogo(jogador, adversario);

		iniciaServicoP2P(porta);

		scanner.close();
    }

	private static void iniciaJogo(Jogador jogador, Jogador adversario) {

		new RegrasJogo(jogador, adversario);

		if (adversario.isDesafiado()) {
			// vez do adversário
			RegrasJogo.turnoAdversario();
		} else {
			//você inicia
			RegrasJogo.turno();
		}
	}

	private static void iniciaServicoP2P(int porta) {
		try {
			SuperTrunfoListener superTrunfoListener = new SuperTrunfoListener(porta);

			do {
				if (!superTrunfoListener.isAlive()) {
					superTrunfoListener.start();
				}
			} while (true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Jogador solicitarInfoServidor(Jogador jogador) {
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

			jogador.setDeck(rSuperTrunfo.distribuirCartas());

			return adversario;
		} catch (RemoteException ex) {
			Logger.getLogger(SuperTrunfoClient.class.getName()).log(Level.SEVERE, null, ex);
		} catch (NotBoundException ex) {
			Logger.getLogger(SuperTrunfoClient.class.getName()).log(Level.SEVERE, null, ex);
		} catch (MalformedURLException ex) {
			Logger.getLogger(SuperTrunfoClient.class.getName()).log(Level.SEVERE, null, ex);
		}

		return null;
	}

	private static int portaAleatoria() {
		try {
			ServerSocket s = new ServerSocket(0);
			int i = s.getLocalPort();
			s.close();
			return i;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
