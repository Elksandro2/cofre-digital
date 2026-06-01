import java.io.Serializable;

public record Pedido (
    String nome,
    int aposta
) implements Serializable
{}