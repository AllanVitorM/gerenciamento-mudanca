package br.com.gerenciamentomudancasapi.controller;

import br.com.gerenciamentomudancasapi.exception.SistemaNaoEncontradoException;
import br.com.gerenciamentomudancasapi.model.Sistema;
import br.com.gerenciamentomudancasapi.service.SistemaService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(SistemaController.class)
class SistemaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SistemaService sistemaService;

    @Test
    void deveBuscarSistemaPorId() throws Exception {
        Sistema sistema = new Sistema("Teste");

        when(sistemaService.buscarSistemaPorId(1))
                .thenReturn(sistema);

        mockMvc.perform(get("/sistemas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Teste"));
    }

    @Test
    void deveLancarExcecaoQuandoSistemaNaoExistir() throws Exception{
        Sistema sistema = new Sistema("Teste");

        when(sistemaService.buscarSistemaPorId(999))
                .thenThrow(new SistemaNaoEncontradoException("Sistema não encontrado"));

        mockMvc.perform(get("/sistemas/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value("Sistema não encontrado"));

    }

    @Test
    void deveAtualizarNomeSistema() throws Exception{
        Sistema sistema = new Sistema("TESTE2");

        when(sistemaService.atualizarSistema(1, "TESTE2"))
                .thenReturn(sistema);

        mockMvc.perform(patch("/sistemas/1")
                .contentType(MediaType.APPLICATION_JSON).content("""
                        {
                            "nome": "TESTE2"
                        }"""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("TESTE2"));

        verify(sistemaService).atualizarSistema(1, "TESTE2");
    }

    @Test
    void deveDesativarSistema() throws Exception{
        Sistema sistema = new Sistema("teste");

        when(sistemaService.atualizarStatus(1, false))
                .thenReturn(sistema);

        sistema.desativar();

        mockMvc.perform(patch("/sistemas/1/status")
                .contentType(MediaType.APPLICATION_JSON).content("""
                        {
                            "ativo": false
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ativo").value(false));

        verify(sistemaService).atualizarStatus(1, false);
    }

    @Test
    void deveAtivarSistema() throws Exception{
        Sistema sistema = new Sistema("teste");

        when(sistemaService.atualizarStatus(1, true))
                .thenReturn(sistema);

        mockMvc.perform(patch("/sistemas/1/status")
                .contentType(MediaType.APPLICATION_JSON).content(
                        """
                            {
                                "ativo": true
                            }        
                        """
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ativo").value(true));

        verify(sistemaService).atualizarStatus(1,true);
    }
}
