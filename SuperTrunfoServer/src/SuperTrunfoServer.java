

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.SuperTrunfo;
import app.interfaces.ISuperTrunfo;

class SuperTrunfoServer {

	private static int porta = 1099;

	public static void main(String[] args) {
		iniciaServico();
    }

	private static void iniciaServico() {
		try {
            ISuperTrunfo superTrunfo = new SuperTrunfo();

            LocateRegistry.createRegistry(porta);

            Naming.rebind("app/SuperTrunfo", superTrunfo);
            System.out.println("Servidor ativo");

        } catch (RemoteException ex) {
            Logger.getLogger(SuperTrunfoServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(SuperTrunfoServer.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
}
