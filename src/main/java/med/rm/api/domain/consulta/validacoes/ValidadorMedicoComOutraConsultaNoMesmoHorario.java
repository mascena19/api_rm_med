package med.rm.api.domain.consulta.validacoes;

import med.rm.api.domain.ValidacaoExeception;
import med.rm.api.domain.consulta.ConsultaRepository;
import med.rm.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoDeConsultas{

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DadosAgendamentoConsulta dados){

        var medicoPossuiOutraConsultaNoMesmoHorario = consultaRepository.existsByMedicoIdAndData(dados.idMedico(), dados.data());

        if(medicoPossuiOutraConsultaNoMesmoHorario){
            throw new ValidacaoExeception("Medico possui consulta agendada nesse mesmo horário");
        }
    }

}
