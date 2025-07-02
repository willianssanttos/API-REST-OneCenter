package onecenter.com.br.ecommerce.pedidos.exception.pagamento;

import onecenter.com.br.ecommerce.utils.Constantes;

public class AtualizarStatusPagamentoException extends RuntimeException{

    public AtualizarStatusPagamentoException(){
        super(Constantes.ErroAtualizarStatusPagamentoPedido);
    }
}
