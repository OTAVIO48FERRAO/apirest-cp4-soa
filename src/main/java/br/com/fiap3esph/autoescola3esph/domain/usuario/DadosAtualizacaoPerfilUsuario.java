package br.com.fiap3esph.autoescola3esph.domain.usuario;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoPerfilUsuario(
        @NotNull
        Long id,

        @NotNull
        Role perfil) {
}
