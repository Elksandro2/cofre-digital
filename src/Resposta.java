import java.io.Serializable;

public class Resposta implements Serializable {
    private String mensagem;

    public Resposta(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return this.mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}