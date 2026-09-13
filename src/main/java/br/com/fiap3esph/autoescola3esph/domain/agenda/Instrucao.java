package br.com.fiap3esph.autoescola3esph.domain.agenda;

import br.com.fiap3esph.autoescola3esph.domain.aluno.Aluno;
import br.com.fiap3esph.autoescola3esph.domain.instrutor.Instrutor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "Instrucao")
@Table(name = "instrucoes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Instrucao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instrutor_id")
    private Instrutor instrutor;

    @Column(name = "data_hora")
    LocalDateTime dataHora;

    private boolean cancelada;

    @Enumerated(EnumType.STRING)
    @Column(name = "motivo_cancelamento")
    private MotivoCancelamento motivoCancelamento;

    @Column(name = "data_cancelamento")
    private LocalDateTime dataCancelamento;

    public Instrucao(Long id, Aluno aluno, Instrutor instrutor, LocalDateTime dataHora) {
        this.id = id;
        this.aluno = aluno;
        this.instrutor = instrutor;
        this.dataHora = dataHora;
        this.cancelada = false;
    }

    public void cancelar(MotivoCancelamento motivo) {
        this.cancelada = true;
        this.motivoCancelamento = motivo;
        this.dataCancelamento = LocalDateTime.now();
    }
}