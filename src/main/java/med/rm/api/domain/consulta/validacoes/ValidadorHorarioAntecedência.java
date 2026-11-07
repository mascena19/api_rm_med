package med.rm.api.domain.consulta.validacoes;

import med.rm.api.domain.ValidacaoExeception;
import med.rm.api.domain.consulta.DadosAgendamentoConsulta;

import java.time.Duration;
import java.time.LocalDateTime;

public class ValidadorHorarioAntecedência {

    public void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();

        var agora = LocalDateTime.now();

        var direncaEmMinutos = Duration.between(agora, dataConsulta).toMinutes();

        if(direncaEmMinutos < 30 ) {
            throw new ValidacaoExeception("Consulta deve ser agendada com antecedência mínica de 30 minutos");
        }

    }

}
