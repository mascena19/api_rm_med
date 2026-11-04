package med.rm.api.domain;


public class ValidacaoExeception extends RuntimeException {
    public ValidacaoExeception(String mensagem) {
        super(mensagem);
    }
}
