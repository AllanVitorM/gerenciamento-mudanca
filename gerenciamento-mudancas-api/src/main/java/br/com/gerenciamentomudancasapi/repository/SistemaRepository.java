package br.com.gerenciamentomudancasapi.repository;

import br.com.gerenciamentomudancasapi.model.Sistema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SistemaRepository
        extends JpaRepository<Sistema, Integer> {
    Optional<Sistema> findByNome(String nome);

    List<Sistema> findByAtivo(Boolean ativo);
}