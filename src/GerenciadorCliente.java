import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

public class GerenciadorCliente implements Runnable {
    private Socket socketCliente;

    public GerenciadorCliente(Socket socketCliente) {
        this.socketCliente = socketCliente;
    }

    @Override
    public void run() {
        System.out.println("Nova thread iniciada.");

        try {
            ObjectOutputStream out = new ObjectOutputStream(socketCliente.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socketCliente.getInputStream());

            Pedido pedido = (Pedido) input.readObject();

            System.out.printf("Aposta do cliente %s no número %d\n", pedido.nome(), pedido.aposta());

            Random random = new Random();
            int numeroSorteado = random.nextInt(1000);

            System.out.println("Número sorteado para " + pedido.nome() + " foi: " + numeroSorteado);

            String mensagem = "";

            synchronized (Servidor.class) {
                Servidor.valorAcumulado += 2.0;

                if (pedido.aposta() == numeroSorteado) {
                    double premio = Servidor.valorAcumulado * 0.6;

                    mensagem = String.format("Cofre aberto, %s! Ganhou R$ %.2f", pedido.nome(), premio);

                    Servidor.valorAcumulado = 0.0;
                } else {
                    mensagem = String.format("Código errado, %s. O cofre tem R$ %.2f acumulados.", pedido.nome(), Servidor.valorAcumulado);
                }
            }

            Resposta resposta = new Resposta(mensagem);

            out.writeObject(resposta);
            out.flush();

            socketCliente.close();
        } catch (Exception e) {
            System.out.println("Erro na comunicação: " + e.getMessage());
        }
    }
}
