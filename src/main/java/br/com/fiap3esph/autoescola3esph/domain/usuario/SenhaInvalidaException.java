package br.com.fiap3esph.autoescola3esph.domain.usuario;

public class SenhaInvalidaException extends RuntimeException {
    public SenhaInvalidaException(String message) {
        super(message);
    }
}
