package br.com.fiap3esph.autoescola3esph.domain.agenda.validacao;

import br.com.fiap3esph.autoescola3esph.domain.agenda.DadosAgendamento;
import br.com.fiap3esph.autoescola3esph.domain.agenda.InstrucaoRepository;
import br.com.fiap3esph.autoescola3esph.domain.agenda.ValidacaoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ValidadorLimiteDiarioAluno implements ValidadorAgendamento {
    private static final int LIMITE_DIARIO = 2;

    private final InstrucaoRepository repository;

    @Override
    public void validar(DadosAgendamento dados) {
        LocalDateTime inicioExpediente = dados.dataHora().withHour(6).withMinute(0);
        LocalDateTime fimExpediente = dados.dataHora().withHour(21).withMinute(0);

        long instrucoesNoDia = repository.countByAlunoIdAndDataHoraBetweenAndCanceladaFalse(
                dados.idAluno(),
                inicioExpediente,
                fimExpediente
        );

        if (instrucoesNoDia >= LIMITE_DIARIO) {
            throw new ValidacaoException("Aluno já possui o limite de duas instruções agendadas para este dia!");
        }
    }
}