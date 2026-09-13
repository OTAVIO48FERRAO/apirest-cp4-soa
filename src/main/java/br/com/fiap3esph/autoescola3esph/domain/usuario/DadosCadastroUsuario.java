package br.com.fiap3esph.autoescola3esph.domain.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DadosCadastroUsuario(
        @NotBlank
        String login,

        @NotBlank
        @Size(min = 6, message = "A senha deve possuir no mínimo 6 caracteres!")
        String senha,

        @NotNull
        Role perfil) {
}
