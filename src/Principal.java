import Cliente.Client;
import Servidor.Servidor;

import java.io.IOException;

public class Principal {
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        Servidor servidor = new Servidor();

        client.setVisible(true);
        servidor.setVisible(true);

        servidor.iniciar();
    }
}