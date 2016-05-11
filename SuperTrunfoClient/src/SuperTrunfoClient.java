

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.Scanner;

import app.RegrasJogo;
import app.SuperTrunfoListener;
import model.Jogador;

public class SuperTrunfoClient {
	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws UnknownHostException {
		String host = InetAddress.getLocalHost().toString();
		int porta = portaAleatoria();
		Jogador jogador, adversario = null;

		System.out.println("Digite seu nome: ");
		jogador = new Jogador(scanner.nextLine(), host, porta);

		// comunica com o servidor
		System.out.println("Iniciando conexão com o servidor...");

		adversario = RegrasJogo.getAdversario(jogador);
		System.out.println("Seu adversário é:"+ adversario.getNome());

		jogador.setDeck(RegrasJogo.getDeck());

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
