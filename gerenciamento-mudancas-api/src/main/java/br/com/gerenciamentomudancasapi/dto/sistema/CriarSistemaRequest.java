package br.com.gerenciamentomudancasapi.dto.sistema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CriarSistemaRequest(
    @NotBlank(message = "O nome do sistema é obrigatório")
    @Size(max = 45, message = "O nome deve ter no máximo 45 caracteres")
    String nome
){}