package onecenter.com.br.ecommerce.pedidos.exception.pagamento;

import onecenter.com.br.ecommerce.utils.Constantes;

public class PagamentoException extends RuntimeException{

    public PagamentoException(){
        super(Constantes.ErroAoSalvarPagamento);
    }
}
