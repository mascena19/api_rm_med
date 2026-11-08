package med.rm.api.domain.consulta.validacoes;

import med.rm.api.domain.ValidacaoExeception;
import med.rm.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoDeConsultas {

    public void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();

        var agora = LocalDateTime.now();

        var direncaEmMinutos = Duration.between(agora, dataConsulta).toMinutes();

        if(direncaEmMinutos < 30 ) {
            throw new ValidacaoExeception("Consulta deve ser agendada com antecedência mínica de 30 minutos");
        }

    }

}
