package br.com.zup.Amazup.livro;

import br.com.zup.Amazup.configs.exception.LivroJaCadastradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
