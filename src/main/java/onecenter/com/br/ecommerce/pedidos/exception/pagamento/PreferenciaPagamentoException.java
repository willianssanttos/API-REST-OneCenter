package onecenter.com.br.ecommerce.pedidos.exception.pagamento;

import onecenter.com.br.ecommerce.utils.Constantes;

public class PreferenciaPagamentoException extends RuntimeException{

    public PreferenciaPagamentoException(){
        super(Constantes.ErroAoSalvarPreferenciaPagamento);
    }
}
