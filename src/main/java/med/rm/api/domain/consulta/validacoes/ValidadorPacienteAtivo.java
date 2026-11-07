package med.rm.api.domain.consulta.validacoes;

import med.rm.api.domain.ValidacaoExeception;
import med.rm.api.domain.consulta.DadosAgendamentoConsulta;
import med.rm.api.domain.paciente.PacienteRepository;

public class ValidadorPacienteAtivo {

    private PacienteRepository pacienteRepository;

    public void validar(DadosAgendamentoConsulta dados) {
        var pacienteEstaAtivo = pacienteRepository.findAtivoByID(dados.idPaciente());
        if(!pacienteEstaAtivo) {
            throw new ValidacaoExeception("Consulta não pode ser agendada com paciente excluído")
        }
    }

}
