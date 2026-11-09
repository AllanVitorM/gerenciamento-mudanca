package br.com.gerenciamentomudancasapi.service;

import br.com.gerenciamentomudancasapi.exception.SistemaExistenteException;
import br.com.gerenciamentomudancasapi.exception.SistemaNaoEncontradoException;
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
            throw new SistemaExistenteException("Já existe um sistema cadastrado com esse nome");
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
                        new SistemaNaoEncontradoException("Sistema não encontrado."));
    }

    public Sistema atualizarSistema(Integer id, String nome){
        Sistema sistema = buscarSistemaPorId(id);

        String nomeNormalizado = nome.trim().toUpperCase();

        if(sistemaRepository.findByNome(nomeNormalizado)
                .filter(existente -> !existente.getId().equals(id)).isPresent()) {
            throw new SistemaExistenteException("já existe um sistema cadastrado com esse nome.");
        }

        sistema.alterarNome(nomeNormalizado);
        return sistemaRepository.save(sistema);
    }

    public Sistema atualizarStatus(Integer id, Boolean ativo){
        Sistema sistema = buscarSistemaPorId(id);

        if(ativo) {
            sistema.ativar();
        } else {
            sistema.desativar();
        }

        return sistemaRepository.save(sistema);
    }
}