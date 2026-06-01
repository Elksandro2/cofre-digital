import java.io.Serializable;

public record Resposta (
    String mensagem
) implements Serializable
{}