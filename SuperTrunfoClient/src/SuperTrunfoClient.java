

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.interfaces.ISuperTrunfo;
import model.Jogador;

public class SuperTrunfoClient {

	private static Jogador jogador;
	private static Jogador adversario;
	private static int porta;

	public static void main(String[] args) throws UnknownHostException {
		Scanner scanner = new Scanner(System.in);
		porta = portaAleatoria();
		String host = InetAddress.getLocalHost().toString() +":"+ porta;

		System.out.println("Digite seu nome: ");
		jogador = new Jogador(scanner.nextLine(), host);

		iniciaServicoEntrada();

		// comunica com o servidor
		try {
			ISuperTrunfo rSuperTrunfo = (ISuperTrunfo) Naming.lookup("rmi://localhost:1099/app/SuperTrunfo");

			System.out.println("Aguardando na fila...");

			do {
				adversario = rSuperTrunfo.buscarJogador(jogador);

				// aguarda 0.5s para verificar a fila
				try { Thread.sleep(500);
				} catch (InterruptedException e) {}
			} while(adversario == null);

			System.out.println("Seu adversário é:"+ adversario.getNome());

			jogador.setDeck(rSuperTrunfo.distribuirCartas());

		} catch (RemoteException ex) {
			Logger.getLogger(SuperTrunfoClient.class.getName()).log(Level.SEVERE, null, ex);
		} catch (NotBoundException ex) {
			Logger.getLogger(SuperTrunfoClient.class.getName()).log(Level.SEVERE, null, ex);
		} catch (MalformedURLException ex) {
			Logger.getLogger(SuperTrunfoClient.class.getName()).log(Level.SEVERE, null, ex);
		}

		// inicia jogo
		if (adversario.isDesafiado()) {

		} else {
			//você inicia
		}

		scanner.close();
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

	private static void iniciaServicoEntrada() {
		try {
//            ISuperTrunfo superTrunfo = new SuperTrunfo();
//            ISistemaEleitoral sistemaEleitoral = new SistemaEleitoral();

            LocateRegistry.createRegistry(porta);

//            Naming.rebind("app/UrnaEleicao", urnaEleicao);
//            Naming.rebind("app/SistemaEleitoral", sistemaEleitoral);

        } catch (RemoteException ex) {
//            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
}
