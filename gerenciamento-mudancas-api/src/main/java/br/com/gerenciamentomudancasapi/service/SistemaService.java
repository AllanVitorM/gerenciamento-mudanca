package br.com.gerenciamentomudancasapi.service;

import br.com.gerenciamentomudancasapi.model.Sistema;
import br.com.gerenciamentomudancasapi.repository.SistemaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SistemaService {

    private final SistemaRepository sistemaRepository;

    public SistemaService(SistemaRepository sistemaRepository) {
        this.sistemaRepository = sistemaRepository;
    }

    public List <Sistema> listarSistemas(){
        return sistemaRepository.findAll();
    }
}
