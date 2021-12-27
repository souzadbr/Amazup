package br.com.zup.Amazup.configs;

import br.com.zup.Amazup.configs.exception.LivroJaCadastradoException;
import br.com.zup.Amazup.configs.exception.LivroNãoEcontradaPorIdException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
@RestControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public List<ErroDeValidacao> manipularErrosDeValidacao(MethodArgumentNotValidException exception) {
        List<ErroDeValidacao> erros = new ArrayList<>();
        for (FieldError fieldError : exception.getFieldErrors()) {
            ErroDeValidacao erroDeValidacao = new ErroDeValidacao(
                    fieldError.getDefaultMessage(), fieldError.getField());
            erros.add(erroDeValidacao);
        }
        return erros;
    }

    @ExceptionHandler(LivroJaCadastradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MensagemDeErro livroJaCadastrado(LivroJaCadastradoException exception) {
        return new MensagemDeErro("Livro já cadastrado");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public MensagemDeErro naoLegivelException() {
        return new MensagemDeErro("Informação do JSON ilegível");

    }

    @ExceptionHandler(LivroNãoEcontradaPorIdException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MensagemDeErro livroNaoEncontrado(LivroNãoEcontradaPorIdException exception) {
        return new MensagemDeErro("Livro não Encontrado");
    }
}
