package app;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

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
		return atributo.getValor() > atributoAdversario.getValor();
	}

	public static boolean perdeu() {
		return JOGADOR.deckIsEmpty();
	}

	public static Carta compararCarta(Carta cartaAdversario, AtributoCarta atributo) {
		Carta cartaEmJogo = JOGADOR.removeCartaTopoDeck();

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
		Scanner scanner = new Scanner(System.in);
		Carta cartaEmJogo = JOGADOR.getCartaTopoDeck();
		Carta cartaAdversario;

		printDetalhesCarta(cartaEmJogo);

		System.out.println("Escolha um atributo para comparar:");
		int iAtributo = scanner.nextInt();

		AtributoCarta atributoEscolhido = cartaEmJogo.getListaAtributos().get(iAtributo);
		System.out.println(atributoEscolhido.getNome() + atributoEscolhido.getValor());

		Socket socketToServer;
		try {
			socketToServer = new Socket("localhost", ADVERSARIO.getPorta());
			ObjectOutputStream outputStream = new ObjectOutputStream(socketToServer.getOutputStream());
			ObjectInputStream inputStream = new ObjectInputStream(socketToServer.getInputStream());

			outputStream.writeObject(cartaEmJogo);

			cartaAdversario = (Carta) inputStream.readObject();
			compararCarta(cartaAdversario, atributoEscolhido);
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
		System.out.println("Carta: "+ carta.getNome());

		for (AtributoCarta atributo : carta.getListaAtributos()) {
			System.out.println(
					"("+ carta.getListaAtributos().indexOf(atributo) +") "
					+ atributo.getNome() +": "
					+ atributo.getValor()
			);
		}
	}
}
