package br.com.gerenciamentomudancasapi.service;

import br.com.gerenciamentomudancasapi.exception.SistemaNaoEncontradoException;
import br.com.gerenciamentomudancasapi.model.Sistema;
import br.com.gerenciamentomudancasapi.repository.SistemaRepository;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class SistemaServiceTest {

    @Test
    void deveBuscarSistemaPorId(){
        SistemaRepository repository = mock(SistemaRepository.class);

        SistemaService service = new SistemaService(repository);

        Sistema sistema = new Sistema("SIG");

        when(repository.findById(1))
                .thenReturn(Optional.of(sistema));

        Sistema resultado = service.buscarSistemaPorId(1);

        assertEquals("SIG", resultado.getNome());

        verify(repository).findById(1);
    }

    @Test
    void deveLancarExcecaoQuandoSistemaNaoExistir(){
        SistemaRepository repository = mock(SistemaRepository.class);

        SistemaService service = new SistemaService(repository);

        when(repository.findById(999))
                .thenReturn(Optional.empty());

        SistemaNaoEncontradoException exception = assertThrows(
                SistemaNaoEncontradoException.class,
                () -> service.buscarSistemaPorId(999)
        );

        assertEquals("Sistema não encontrado.", exception.getMessage());

        verify(repository).findById(999);
    }

    @Test
    void deveAtualizarNomeDoSistema(){
        SistemaRepository repository = mock(SistemaRepository.class);

        SistemaService service = new SistemaService(repository);

        Sistema sistema = new Sistema("SIG");

        String nome = "Testando";
        String nomeNormalizado = nome.toUpperCase(Locale.ROOT);

        when(repository.findById(1))
                .thenReturn(Optional.of(sistema));

        when(repository.findByNome(nomeNormalizado))
                .thenReturn(Optional.empty());

        when(repository.save(sistema))
                .thenReturn(sistema);

        Sistema resultado = service.atualizarSistema(1, nome);

        assertEquals(nomeNormalizado, resultado.getNome());

        verify(repository).save(sistema);
        verify(repository).findById(1);
        verify(repository).findByNome(nomeNormalizado);

    }
}
