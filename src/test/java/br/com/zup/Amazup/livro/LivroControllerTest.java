package br.com.zup.Amazup.livro;

import br.com.zup.Amazup.autor.Autor;
import br.com.zup.Amazup.componentes.Conversor;
import br.com.zup.Amazup.componentes.UriContrutor;
import br.com.zup.Amazup.enums.Genero;
import br.com.zup.Amazup.livro.dtos.LivroDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest({LivroController.class, UriContrutor.class, Conversor.class})
public class LivroControllerTest {
    @MockBean
    private LivroService livroService;

    @Autowired
    private MockMvc mockMvc;


    private ObjectMapper objectMapper;
    private Livro livro;
    private Autor autor;
    private LivroDTO livroDTO;

    @BeforeEach //sempre é executado antes de cada teste
    private void setup(){
        objectMapper = new ObjectMapper();

        livro = new Livro();
        livro.setGenero(Genero.FICCAO_CIENTIFICA);
        livro.setNome("Adeus e Obrigado Pelos Peixes");
        livro.setId(100000);

        autor = new Autor();
        autor.setId(1);
        autor.setNome("Douglas Adams");

        livro.setAutor(autor);
    }

    @Test
    public void testarCadastroDeLivro() throws Exception{
        Mockito.when(livroService.salvarLivro(Mockito.any(Livro.class))).thenReturn(livro);
        String json = objectMapper.writeValueAsString(livro);

        ResultActions resultadoDaRequisicao = mockMvc.perform(MockMvcRequestBuilders.post("/livros")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().is(201))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.vitrine")
                                .value("http://localhost:8080/livros/"+livro.getId())
                );
    }

    @Test
    public void testeRotaParaCadastrarLivroValidacaoAutor()throws Exception{
        Mockito.when(livroService.salvarLivro(Mockito.any(Livro.class))).thenReturn(livro);
        livro.setAutor(null);
        String json = objectMapper.writeValueAsString(livro);

        ResultActions respostaDaRequsicao = mockMvc.perform(MockMvcRequestBuilders.post("/livros")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    public void testeRotaParaCadastrarLivroValidacaoPreçoNull()throws Exception{
        Mockito.when(livroService.salvarLivro(Mockito.any(Livro.class))).thenReturn(livro);
        livro.setPreco(0);
        String json = objectMapper.writeValueAsString(livro);

        ResultActions respostaDaRequsicao = mockMvc.perform(MockMvcRequestBuilders.post("/livros")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    public void testeRotaParaCadastrarLivroValidacaoNomeNull()throws Exception{
        Mockito.when(livroService.salvarLivro(Mockito.any(Livro.class))).thenReturn(livro);
        livro.setNome(null);
        String json = objectMapper.writeValueAsString(livro);

        ResultActions respostaDaRequsicao = mockMvc.perform(MockMvcRequestBuilders.post("/livros")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test //teste de validação recebendo um nome vazio
    public void testeRotaParaCadastrarLivroValidacaoNomeBlank()throws Exception{ //nome do método + throws Exception
        // que indica que pode gerar uma exceção
        Mockito.when(livroService.salvarLivro(Mockito.any(Livro.class))).thenReturn(livro);//simula o quue a
        // livroService deve fazer usando mockito retornando um livro
        livro.setNome(" ");//passa um livro com nome nulo
        String json = objectMapper.writeValueAsString(livro);//transforma a String em JSON


        ResultActions respostaDaRequsicao = mockMvc.perform(MockMvcRequestBuilders.post("/livros")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().is(422));
    } //Passa que a resposta da requisição vem pelo caminho /livros para postar a informação pelo verbo post
    // usando json e que a resposta esperada é 422 ..
    // .ou seja passando  livro.setNome(" ") a resposta esperada é 422;

    @Test
    public void testeRotaParaCadastrarLivroValidacaoGeneroNull()throws Exception{
        Mockito.when(livroService.salvarLivro(Mockito.any(Livro.class))).thenReturn(livro);
        livro.setGenero(null);
        String json = objectMapper.writeValueAsString(livro);

        ResultActions respostaDaRequsicao = mockMvc.perform(MockMvcRequestBuilders.post("/livros")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    public void testarRotaParaCadastrarGeneroValid()throws Exception{
        Mockito.when(livroService.salvarLivro(Mockito.any(Livro.class))).thenReturn(livro);
        String json = objectMapper.writeValueAsString(livro);
        json = json.replace("FICCAO_CIENTIFICA","TESTE");

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/livros")
                .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    public void testarRotaDeCadastroDeLivroComPreçoAbaixoDoMinimo()throws Exception{
        Mockito.when(livroService.salvarLivro(livro)).thenReturn(livro);
        livro.setPreco(0);
        String json = objectMapper.writeValueAsString(livro);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/livros")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(422));

    }

    @Test
    public void testarDeletarLivro() throws Exception {
        livro.setId(1); // passo o id que desejo testar
        Mockito.doNothing().when(livroService).deletarLivro(Mockito.anyInt()); //mockito o livroService e simulo deletar
        // qualquer numero inteiro

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.delete("/livros"+livro.getId())
                //deleta o livro + id passado que é um JSON
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(204));
                //o esperado aqui é uma resposta 204 ...ok feito com sucesso
        Mockito.verify(livroService, Mockito.times(1)).deletarLivro(Mockito.anyInt());
        //mockito verify verifica se o método foi execultado 1x.
    }









    //Validações: eço Not NuAutor Not Null, Prll, Nome do Livro Not Null Not blank,
    // Limitar casas decimais no preço, preço não pode menor que 0, genero Not NULL, genero Valido
}
