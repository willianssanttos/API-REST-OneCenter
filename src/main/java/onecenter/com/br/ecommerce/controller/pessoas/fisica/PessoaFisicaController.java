package onecenter.com.br.ecommerce.controller.pessoas.fisica;

import onecenter.com.br.ecommerce.dto.pessoas.request.fisica.PessoaFisicaRequest;
import onecenter.com.br.ecommerce.dto.pessoas.response.fisica.PessoaFisicaResponse;
import onecenter.com.br.ecommerce.service.pessoas.fisica.PessoaFisicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/pessoa")
public class PessoaFisicaController {

    @Autowired
    private PessoaFisicaService pessoaFisicaService;

    @PostMapping("/fisica")
    public ResponseEntity<PessoaFisicaResponse> pessoaFisica(@RequestBody PessoaFisicaRequest pessoaFisica){
        return new ResponseEntity<>(pessoaFisicaService.cadastrarPessoaFisica(pessoaFisica), HttpStatus.CREATED);
    }

    @GetMapping("/buscar/{CPF}")
    public ResponseEntity<PessoaFisicaResponse> buscarPorCpf(@PathVariable String CPF){
        if(CPF.length() == 11){
           PessoaFisicaResponse response = pessoaFisicaService.obterPorCpf(CPF);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/")
    public ResponseEntity<List<PessoaFisicaResponse>> obterTodosProdutos(){
        List<PessoaFisicaResponse> response = pessoaFisicaService.obterTodasPessoas();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/atualizar-dados/{cpf}")
    public ResponseEntity<PessoaFisicaResponse> alterarDados(@PathVariable String cpf, @RequestBody PessoaFisicaResponse editar) {
        if (cpf.length() == 11) {
            editar.setCpf(cpf);
            PessoaFisicaResponse response = pessoaFisicaService.atualizarDados(editar);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.badRequest().build();
    }

}
