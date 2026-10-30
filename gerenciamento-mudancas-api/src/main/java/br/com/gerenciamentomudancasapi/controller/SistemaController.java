package br.com.gerenciamentomudancasapi.controller;

import br.com.gerenciamentomudancasapi.dto.sistema.CriarSistemaRequest;
import br.com.gerenciamentomudancasapi.model.Sistema;
import br.com.gerenciamentomudancasapi.service.SistemaService;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/sistemas")
public class SistemaController {
    private  final SistemaService sistemaService;

    public SistemaController(SistemaService sistemaService) {
        this.sistemaService = sistemaService;
    }

    @PostMapping("/criarsistemas")
    public ResponseEntity<Sistema> criar(
            @Valid @RequestBody CriarSistemaRequest request
            ) {
        Sistema sistema = sistemaService.criarSistema(request.nome());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(sistema);
    }

    @GetMapping
    public List<Sistema>listar(
            @RequestParam(required = false) Boolean ativo
    ) {
        if(ativo != null) {
            return sistemaService.listarPorStatus(ativo);
        }

        return sistemaService.listarSistemas();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Sistema>buscarPorId(
            @PathVariable Integer id
    ) {
        Sistema sistema = sistemaService.buscarSistemaPorId(id);

        return ResponseEntity.ok(sistema);
    }
}
