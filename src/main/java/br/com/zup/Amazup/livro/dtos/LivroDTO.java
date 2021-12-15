package br.com.zup.Amazup.livro.dtos;

import br.com.zup.Amazup.autor.Autor;
import br.com.zup.Amazup.autor.dtos.AutorUriDTO;
import br.com.zup.Amazup.enums.Genero;
import org.apache.logging.log4j.message.Message;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LivroDTO {
    private int id;
    @NotBlank(message =  "Esse campo não deve ser vazio")
    private String nome;
    @NotNull(message =  "Esse campo não deve ser vazio")
    private Genero genero;
    @NotNull(message =  "Esse campo não deve ser vazio")
    private AutorUriDTO autor;
    @Min(value = 1)
    private double preco;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public AutorUriDTO getAutor() {
        return autor;
    }

    public void setAutor(AutorUriDTO autor) {
        this.autor = autor;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
