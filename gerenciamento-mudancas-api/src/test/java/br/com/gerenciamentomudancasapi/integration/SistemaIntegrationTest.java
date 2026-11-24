package br.com.gerenciamentomudancasapi.integration;

import br.com.gerenciamentomudancasapi.config.TestcontainersConfiguration;
import br.com.gerenciamentomudancasapi.model.Sistema;
import br.com.gerenciamentomudancasapi.repository.SistemaRepository;
import br.com.gerenciamentomudancasapi.service.SistemaService;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AutoConfigureMockMvc
@SpringBootTest(properties = "spring.datasource.password=unused")
@Import(TestcontainersConfiguration.class)
public class SistemaIntegrationTest {

    @Autowired
    private SistemaService sistemaService;

    @Autowired
    private SistemaRepository sistemaRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveCadastrarSistemaNoBancoDeDados(){

        Sistema sistema = sistemaService.criarSistema("SIG_TEST");

        Sistema encontrado = sistemaRepository
                .findById(sistema.getId())
                .orElseThrow();

        assertEquals("SIG_TEST", encontrado.getNome());
        assertTrue(encontrado.getAtivo());

    }

    @Test
    void deveCadastrarSistemaPelaApi() throws Exception{
        mockMvc.perform(post("/sistemas/criarsistemas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        """
                        {
                            "nome": "TESTE"
                        }        
                        """)
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("TESTE"));

        Sistema sistemaSalvo = sistemaRepository
                .findByNome("TESTE")
                .orElseThrow();
        assertEquals("TESTE", sistemaSalvo.getNome());
    }

    @Test
    void deveRetornarTodosSistemasPelaApi() throws Exception{
        Sistema sistema = sistemaRepository.save(new Sistema("TESTE"));

        mockMvc.perform(get("/sistemas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].nome")
                        .value(hasItem("TESTE")));

        List<Sistema> todosSistemas = sistemaRepository.findAll();

        assertTrue(
                todosSistemas.stream()
                        .anyMatch(s -> s.getId().equals(sistema.getId()))
        );
    }
}
