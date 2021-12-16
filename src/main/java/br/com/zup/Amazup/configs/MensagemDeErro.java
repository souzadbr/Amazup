package br.com.zup.Amazup.configs;

public class MensagemDeErro {
    private String mensagem;

    public MensagemDeErro(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
