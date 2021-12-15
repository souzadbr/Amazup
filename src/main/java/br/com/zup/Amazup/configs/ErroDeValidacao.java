package br.com.zup.Amazup.configs;

public class ErroDeValidacao {
    private String campo;
    private String mensagem;

    public ErroDeValidacao(String mensagem, String campo) {
        this.mensagem = mensagem;
        this.campo = campo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }
}
