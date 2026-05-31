import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static double valorAcumulado = 0.0;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8079)) {
            System.out.println("Servidor iniciado na porta 8079.");

            while (true) {
                Socket socketCliente = serverSocket.accept();
                System.out.println("Novo cliente conectado!");

                GerenciadorCliente gerenciadorCliente = new GerenciadorCliente(socketCliente);

                Thread thread = new Thread(gerenciadorCliente);
                thread.start();
            }
        } catch (IOException e) {
            System.out.println("Erro ao iniciar: " + e.getMessage());
        }
    }
}
