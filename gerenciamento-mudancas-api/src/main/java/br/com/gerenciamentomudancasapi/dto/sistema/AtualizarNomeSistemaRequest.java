package br.com.gerenciamentomudancasapi.dto.sistema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AtualizarNomeSistemaRequest(
        @NotBlank(message = "O nome do sistema é obrigatório")
        @Size(max = 120, message = "O nome deve ter no máximo 120 caracteres")
        String nome
) {}
