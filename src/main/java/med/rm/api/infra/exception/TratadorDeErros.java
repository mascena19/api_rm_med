package med.rm.api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import med.rm.api.domain.ValidacaoExeception;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex){

        var erros = ex.getFieldErrors();

        return ResponseEntity.badRequest().body(erros.stream().map(
                DadosErrovalidacao::new
        ).toList());
    }

    private record DadosErrovalidacao(
            String campo,
            String mensagem
    ) {
        public DadosErrovalidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }

    @ExceptionHandler(ValidacaoExeception.class)
    public ResponseEntity tratarErroRegraDeNegocio(ValidacaoExeception ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }


}
