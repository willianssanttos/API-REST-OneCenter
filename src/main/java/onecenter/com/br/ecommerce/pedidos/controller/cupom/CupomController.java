package onecenter.com.br.ecommerce.pedidos.controller.cupom;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import onecenter.com.br.ecommerce.pedidos.dto.request.CupomRequest;
import onecenter.com.br.ecommerce.pedidos.dto.response.CupomResponse;
import onecenter.com.br.ecommerce.pedidos.service.cupom.CupomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/cupom")
public class CupomController implements ICupomController{

    @Autowired
    private CupomService cupomService;

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<CupomResponse> cadastrarCupom(@RequestBody CupomRequest cupom){
        return new ResponseEntity<>(cupomService.cadastrarCupom(cupom), HttpStatus.CREATED);
    }
}
