package br.com.zup.Amazup.livro;

import br.com.zup.Amazup.configs.exception.LivroJaCadastradoException;
import br.com.zup.Amazup.configs.exception.LivroNãoEcontradaPorIdException;
import br.com.zup.Amazup.enums.Genero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.GeneratedValue;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LivroService {
    @Autowired
    private LivroRepository livroRepository;

    public Livro salvarLivro(Livro livro) {
        Livro livroEncontrado = livroRepository.findByNome(livro.getNome());
        if (livro.getAutor().getId() == livroEncontrado.getAutor().getId()) {
            throw new LivroJaCadastradoException();
        }

        return livroRepository.save(livro);
    }

    public Livro buscarLivroPorId(int id){
        Optional<Livro> livroOptional = livroRepository.findById(id);
        if(livroOptional.isEmpty()){
            throw new RuntimeException();
        }

        return livroOptional.get();
    }

    //Atualizar livro
    public Livro atualizarDedosLivcro(int id, Genero genero,String nome, double preco) {
        Livro livro  = buscarLivroPorId(id);
        livro.setGenero(genero);
        livro.setNome(nome);
        livro.setPreco(preco);
        livroRepository.save(livro);

        return livro;
    }

    //Deletar livro
    public void deletarLivro(int id){
        if(livroRepository.existsById(id)){
            livroRepository.deleteById(id);
        }else {
            throw new LivroNãoEcontradaPorIdException("Livro não encontrada!");
        }
    }
    //Vertificar livros cadastrados






}
