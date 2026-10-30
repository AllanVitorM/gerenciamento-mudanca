package br.com.gerenciamentomudancasapi.service;

import br.com.gerenciamentomudancasapi.model.Sistema;
import br.com.gerenciamentomudancasapi.repository.SistemaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SistemaService {

    private final SistemaRepository sistemaRepository;

    //POST
    public Sistema criarSistema(String nome) {
        String nomeNormalizado = nome.trim();

        if(sistemaRepository.findByNome(nomeNormalizado).isPresent()){
            throw new IllegalArgumentException("Já existe um sistema cadastrado com esse nome");
        }

        Sistema sistema = new Sistema(nomeNormalizado);

        return sistemaRepository.save(sistema);
    }

    //LIST
    public SistemaService(SistemaRepository sistemaRepository) {
        this.sistemaRepository = sistemaRepository;
    }

    public List<Sistema> listarSistemas(){
        return sistemaRepository.findAll();
    }

    public List<Sistema> listarPorStatus(Boolean ativo) {
        return sistemaRepository.findByAtivo(ativo);
    }

    public Sistema buscarSistemaPorId(Integer id) {
        return sistemaRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Sistema não encontrado."));
    }
}