import java.io.Serializable;

public class Pedido implements Serializable {
    private String nome;
    private Integer aposta;

    public Pedido(String nome, Integer valorAposta) {
        this.nome = nome;
        this.aposta = valorAposta;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAposta() {
        return this.aposta;
    }

    public void setAposta(Integer valorAposta) {
        this.aposta = valorAposta;
    }
}