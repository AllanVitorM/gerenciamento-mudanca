package br.com.gerenciamentomudancasapi.dto.sistema;

import jakarta.validation.constraints.NotNull;

public record AtualizarStatusSistemaRequest(
        @NotNull(message = "O status do sistema é obrigatório")
        Boolean ativo
) {}
