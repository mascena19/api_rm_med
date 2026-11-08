package med.rm.api.domain.consulta.validacoes;

import med.rm.api.domain.ValidacaoExeception;
import med.rm.api.domain.consulta.DadosAgendamentoConsulta;
import med.rm.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoDeConsultas {

    @Autowired
    private PacienteRepository pacienteRepository;

    public void validar(DadosAgendamentoConsulta dados) {
        var pacienteEstaAtivo = pacienteRepository.existsByIdAndAtivoTrue(dados.idPaciente());
        if(!pacienteEstaAtivo) {
            throw new ValidacaoExeception("Consulta não pode ser agendada com paciente excluído");
        }
    }

}
