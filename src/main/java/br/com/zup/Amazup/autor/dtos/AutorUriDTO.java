package br.com.zup.Amazup.autor.dtos;

import javax.validation.constraints.NotNull;
import java.net.URI;

public class AutorUriDTO {
    public URI uri;
    @NotNull
    public String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }
}
