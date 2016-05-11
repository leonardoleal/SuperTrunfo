package app;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ServerSocketFactory;

import model.AtributoVelocidade;
import model.Carta;

public class SuperTrunfoListener extends Thread {
	private static final long serialVersionUID = 1L;

        private ServerSocket serverSocket;

        public SuperTrunfoListener(int port) throws IOException {
            serverSocket = ServerSocketFactory.getDefault().createServerSocket(port);
        }

        @Override
        public void run() {
            while (true) {
                try {
                    final Socket socketToClient = serverSocket.accept();
                    ClientHandler clientHandler = new ClientHandler(socketToClient);
                    clientHandler.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    static class ClientHandler extends Thread{
        private Socket socket;
        ObjectInputStream inputStream;
        ObjectOutputStream outputStream;

        ClientHandler(Socket socket) throws IOException {
            this.socket = socket;
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream= new ObjectOutputStream(socket.getOutputStream());
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Object cartaAdversario = inputStream.readObject();
                    System.out.println("Read object: "+((Carta) cartaAdversario).getNome());

                    // pega carta
                    outputStream.writeObject(
                    		RegrasJogo.compararCarta((Carta) cartaAdversario, new AtributoVelocidade(157))
    				);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
