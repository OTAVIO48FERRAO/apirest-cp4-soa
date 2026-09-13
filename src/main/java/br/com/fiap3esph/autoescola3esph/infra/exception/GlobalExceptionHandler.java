package br.com.fiap3esph.autoescola3esph.infra.exception;

import br.com.fiap3esph.autoescola3esph.domain.agenda.InstrucaoNotFoundException;
import br.com.fiap3esph.autoescola3esph.domain.agenda.ValidacaoException;
import br.com.fiap3esph.autoescola3esph.domain.aluno.AlunoNotFoundException;
import br.com.fiap3esph.autoescola3esph.domain.instrutor.InstrutorNotFoundException;
import br.com.fiap3esph.autoescola3esph.domain.usuario.SenhaInvalidaException;
import br.com.fiap3esph.autoescola3esph.domain.usuario.UsuarioNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Stream;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> tratarGenericNotFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Stream<DadosBadRequest>> tratarBadRequest(MethodArgumentNotValidException e) {
        List<FieldError> erros = e.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosBadRequest::new));
    }

    @ExceptionHandler(InstrutorNotFoundException.class)
    public ResponseEntity<DadosMessage> tratarInstrutorNotFound(InstrutorNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DadosMessage(e.getMessage()));
    }

    @ExceptionHandler(AlunoNotFoundException.class)
    public ResponseEntity<DadosMessage> tratarAlunoNotFound(AlunoNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DadosMessage(e.getMessage()));
    }

    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<DadosMessage> tratarUsuarioNotFound(UsuarioNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DadosMessage(e.getMessage()));
    }

    @ExceptionHandler(InstrucaoNotFoundException.class)
    public ResponseEntity<DadosMessage> tratarInstrucaoNotFound(InstrucaoNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DadosMessage(e.getMessage()));
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<DadosMessage> tratarValidacao(ValidacaoException e) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new DadosMessage(e.getMessage()));
    }

    @ExceptionHandler(SenhaInvalidaException.class)
    public ResponseEntity<DadosMessage> tratarSenhaInvalida(SenhaInvalidaException e) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new DadosMessage(e.getMessage()));
    }

    private record DadosBadRequest(String campo, String mensagem) {
        public DadosBadRequest(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }

    private record DadosMessage(String mensagem) {
    }
}