package br.com.fiap3esph.autoescola3esph.controller;

import br.com.fiap3esph.autoescola3esph.domain.agenda.DadosAgendamento;
import br.com.fiap3esph.autoescola3esph.domain.agenda.DetalhamentoAgendamento;
import br.com.fiap3esph.autoescola3esph.domain.agenda.Instrucao;
import br.com.fiap3esph.autoescola3esph.domain.agenda.InstrucaoRepository;
import br.com.fiap3esph.autoescola3esph.domain.aluno.Aluno;
import br.com.fiap3esph.autoescola3esph.domain.aluno.AlunoRepository;
import br.com.fiap3esph.autoescola3esph.domain.instrutor.Instrutor;
import br.com.fiap3esph.autoescola3esph.domain.instrutor.InstrutorRepository;
import br.com.fiap3esph.autoescola3esph.service.AgendaDeInstrucoes;
import br.com.fiap3esph.autoescola3esph.domain.agenda.DadosCancelamento;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instrucoes")
@RequiredArgsConstructor
public class InstrucaoController {
    private final AgendaDeInstrucoes agenda;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity agendarInstrucao(@RequestBody @Valid DadosAgendamento dados) {
        return ResponseEntity.ok(agenda.agendar(dados));
    }

    @PutMapping("/{id}/cancelamento")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity cancelarInstrucao(
            @PathVariable Long id,
            @RequestBody @Valid DadosCancelamento dados) {
        return ResponseEntity.ok(agenda.cancelar(id, dados));
    }
}