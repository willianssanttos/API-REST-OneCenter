package onecenter.com.br.ecommerce.pessoa.controller.juridica;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import onecenter.com.br.ecommerce.pessoa.dto.pessoas.request.juridico.PessoaJuridicaRequest;
import onecenter.com.br.ecommerce.pessoa.dto.pessoas.response.juridica.PessoaJuridicaResponse;
import onecenter.com.br.ecommerce.pessoa.service.pessoas.juridica.PessoaJuridicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/pessoa/juridica")
public class PessoaJuridicaController implements  IPessoaJuridicaController{

    @Autowired
    private PessoaJuridicaService pessoaJuridicaService;

    @PostMapping("/")
    public ResponseEntity<PessoaJuridicaResponse> pessoaJuridica(@RequestBody PessoaJuridicaRequest pessoaJuridica){
        return new ResponseEntity<>(pessoaJuridicaService.cadastrarPessoaJuridica(pessoaJuridica), HttpStatus.CREATED);
    }

    @GetMapping("/buscar/{CNPJ}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<PessoaJuridicaResponse> buscarPorCnpj(@PathVariable String CNPJ){
        if (CNPJ.length() == 14){
            PessoaJuridicaResponse response = pessoaJuridicaService.obterPorCnpj(CNPJ);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/atualizar-dados/{CNPJ}")
    @PreAuthorize("hasRole('CLIENTE')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<PessoaJuridicaResponse> alterarDados(@PathVariable String CNPJ, @RequestBody PessoaJuridicaRequest editar){
        if( CNPJ.length() == 14){
            editar.setCnpj(CNPJ);
            PessoaJuridicaResponse response = pessoaJuridicaService.atualizarDados(editar);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.badRequest().build();
    }
}
