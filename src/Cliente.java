import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Cofre Digital ===");

        System.out.println("Seu nome: ");
        String nome = scanner.nextLine();

        boolean jogarNovamente = true;

        while (jogarNovamente) {
            int numeroAposta;
            do {
                System.out.println("Número (0 a 999): ");
                numeroAposta = scanner.nextInt();

                if (numeroAposta < 0 || numeroAposta > 999) {
                    System.out.println("Aposta inválida! O número deve estar entre 0 e 999.");
                }
            } while (numeroAposta < 0 || numeroAposta > 999);

            try (Socket socket = new Socket("127.0.0.1", 8079)) {
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

                Pedido pedido = new Pedido(nome, numeroAposta);
                out.writeObject(pedido);
                out.flush();

                Resposta resposta = (Resposta) input.readObject();

                System.out.println("=== Resultado ===");
                System.out.println(resposta.mensagem());
                System.out.println("=================\n");

                if (resposta.mensagem().toLowerCase().startsWith("cofre aberto")) {
                    jogarNovamente = false;
                } else {
                    System.out.print("Deseja tentar novamente? (S/N): ");
                    String tentar = scanner.next();

                    if (tentar.equalsIgnoreCase("n")) {
                        jogarNovamente = false;
                    }
                }

            } catch (Exception e) {
                System.out.println("Erro na conexão: " + e.getMessage());
                jogarNovamente = false;
            }

        }
        scanner.close(); 
    }
}
