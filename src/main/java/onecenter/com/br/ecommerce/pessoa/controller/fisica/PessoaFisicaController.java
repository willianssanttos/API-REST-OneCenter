package onecenter.com.br.ecommerce.pessoa.controller.fisica;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import onecenter.com.br.ecommerce.pessoa.dto.pessoas.request.fisica.PessoaFisicaRequest;
import onecenter.com.br.ecommerce.pessoa.dto.pessoas.response.fisica.PessoaFisicaResponse;
import onecenter.com.br.ecommerce.pessoa.service.pessoas.fisica.PessoaFisicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/pessoa/fisica")
public class PessoaFisicaController implements IPessoaFisicaController{

    @Autowired
    private PessoaFisicaService pessoaFisicaService;

    @PostMapping("/")
    public ResponseEntity<PessoaFisicaResponse> pessoaFisica(@RequestBody PessoaFisicaRequest pessoaFisica){
        return new ResponseEntity<>(pessoaFisicaService.cadastrarPessoaFisica(pessoaFisica), HttpStatus.CREATED);
    }

    @GetMapping("/buscar/{CPF}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<PessoaFisicaResponse> buscarPorCpf(@PathVariable String CPF){
        if(CPF.length() == 11){
           PessoaFisicaResponse response = pessoaFisicaService.obterPorCpf(CPF);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/atualizar-dados/{CPF}")
    @PreAuthorize("hasRole('CLIENTE')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<PessoaFisicaResponse> alterarDados(@PathVariable String CPF, @RequestBody PessoaFisicaRequest editar) {
        if (CPF.length() == 11) {
            editar.setCpf(CPF);
            PessoaFisicaResponse response = pessoaFisicaService.atualizarDados(editar);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.badRequest().build();
    }
}
