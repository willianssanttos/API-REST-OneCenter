package onecenter.com.br.ecommerce.pessoa.controller.juridica;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import onecenter.com.br.ecommerce.pessoa.dto.pessoas.request.juridico.PessoaJuridicaRequest;
import onecenter.com.br.ecommerce.pessoa.dto.pessoas.response.juridica.PessoaJuridicaResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface IPessoaJuridicaController {

    @Operation(summary = "Cadastro de pessoa juridica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cadastro realizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro ao realizar o cadastro!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao realizar cadastro!")
    })
    ResponseEntity<PessoaJuridicaResponse> pessoaJuridica(@RequestBody PessoaJuridicaRequest pessoaJuridica);

    @Operation(summary = "Cliente localizado por CNPJ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente recuperado com sucesso!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao localizar cliente!")
    })
    ResponseEntity<PessoaJuridicaResponse> buscarPorCnpj(@PathVariable String CNPJ);

    @Operation(summary = "Operação para atualizar dados pessoa juridica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "dados atualizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar dados!"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao atualizar dados!")
    })
    ResponseEntity<PessoaJuridicaResponse> alterarDados(@PathVariable String CNPJ, @RequestBody PessoaJuridicaRequest editar);
}
