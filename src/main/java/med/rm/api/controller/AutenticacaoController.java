package med.rm.api.controller;

import jakarta.validation.Valid;
import med.rm.api.domain.usuario.DadosAutenticacao;
import med.rm.api.domain.usuario.Usuario;
import med.rm.api.infra.security.DadosTokenJWT;
import med.rm.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetarLogin(@RequestBody @Valid DadosAutenticacao dados) {

        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = manager.authenticate(authenticationToken);

        var tokenJWT = tokenService.gerarToken( (Usuario) authentication.getPrincipal());

        return ResponseEntity.ok( new DadosTokenJWT(tokenJWT));

    }

}
