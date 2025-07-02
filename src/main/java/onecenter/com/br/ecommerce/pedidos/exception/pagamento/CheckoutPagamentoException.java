package onecenter.com.br.ecommerce.pedidos.exception.pagamento;

import onecenter.com.br.ecommerce.utils.Constantes;

public class CheckoutPagamentoException extends RuntimeException{

    public CheckoutPagamentoException(){
        super(Constantes.ErroAoGerarCheckoutDePagamento);
    }
}
