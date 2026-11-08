package med.rm.api.domain.consulta.validacoes;

import med.rm.api.domain.ValidacaoExeception;
import med.rm.api.domain.consulta.DadosAgendamentoConsulta;
import med.rm.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoDeConsultas {

    @Autowired
    private MedicoRepository medicoRepository;

    public void validar(DadosAgendamentoConsulta dados){

        if(dados.idMedico() == null){
            return;
        }

        var medicoEstaAtivo = medicoRepository.findAtivoById(dados.idMedico());
        if(!medicoEstaAtivo){
            throw new ValidacaoExeception("Consulta não ser agendade com médico excluído");
        }

    }

}
