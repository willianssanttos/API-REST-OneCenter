package onecenter.com.br.ecommerce.pessoa.controller.fisica;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import onecenter.com.br.ecommerce.pessoa.dto.pessoas.request.fisica.PessoaFisicaRequest;
import onecenter.com.br.ecommerce.pessoa.dto.pessoas.response.fisica.PessoaFisicaResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface IPessoaFisicaController {

    @Operation(summary = "Cadastro de pessoa fisica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cadastro realizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro ao realizar o cadastro!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao realizar cadastro!")
    })
    ResponseEntity<PessoaFisicaResponse> pessoaFisica(@RequestBody PessoaFisicaRequest pessoaFisica);

    @Operation(summary = "Cliente localizado por CPF")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente recuperado com sucesso!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao localizar cliente!")
    })
    ResponseEntity<PessoaFisicaResponse> buscarPorCpf(@PathVariable String cpf);

    @Operation(summary = "Operação para atualizar dados pessoa fisica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "dados atualizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar dados!"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao atualizar dados!")
    })
    ResponseEntity<PessoaFisicaResponse> alterarDados(@PathVariable String cpf, @RequestBody PessoaFisicaRequest editar);
}
