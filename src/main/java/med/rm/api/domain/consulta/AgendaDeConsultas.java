package med.rm.api.domain.consulta;


import med.rm.api.domain.ValidacaoExeception;
import med.rm.api.domain.consulta.validacoes.ValidadorAgendamentoDeConsultas;
import med.rm.api.domain.medico.Medico;
import med.rm.api.domain.medico.MedicoRepository;
import med.rm.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoDeConsultas> validadores;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados){

        if(!pacienteRepository.existsById(dados.idPaciente())){
            throw new ValidacaoExeception("Id do paciente informado não existe!");
        }

        if(dados.idMedico() != null &&  !medicoRepository.existsById(dados.idMedico())){
            throw new ValidacaoExeception("Id do médico informado não existe!");
        }

        validadores.forEach(v -> v.validar(dados));

        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var medico = escolherMedico(dados);

        if (medico == null) {
            throw new ValidacaoExeception("Não existe médico disponível nessa data!");
        }

        var consulta = new Consulta(null, medico, paciente, dados.data() );


        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {

        if(dados.idMedico() != null){
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if (dados.especialidade() == null){
            throw new ValidacaoExeception("Especialidade é obrigatória quando médico não for escolhido!");
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData(
                        dados.especialidade(),
                        dados.data()

                )
                .stream()
                .findFirst()
                .orElseThrow(() -> new ValidacaoExeception(
                        "Não existe médico disponível para a data informada!"
                ));

    }

}
