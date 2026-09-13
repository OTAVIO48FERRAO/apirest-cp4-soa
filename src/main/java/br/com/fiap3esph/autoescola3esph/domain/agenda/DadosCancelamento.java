package br.com.fiap3esph.autoescola3esph.domain.agenda;

import jakarta.validation.constraints.NotNull;

public record DadosCancelamento(
        @NotNull
        MotivoCancelamento motivo) {
}
