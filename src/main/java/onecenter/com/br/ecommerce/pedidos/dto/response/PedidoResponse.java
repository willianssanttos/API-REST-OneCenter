package onecenter.com.br.ecommerce.pedidos.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import onecenter.com.br.ecommerce.pessoa.dto.pessoas.response.PessoaResponse;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponse {

    private Integer idPedido;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private Timestamp dataPedido;
    private String statusPedido;
    private BigDecimal descontoAplicado;
    private BigDecimal valorTotal;
    private PessoaResponse cliente;
    private List<ItemPedidoResponse> itemPedido;

}
