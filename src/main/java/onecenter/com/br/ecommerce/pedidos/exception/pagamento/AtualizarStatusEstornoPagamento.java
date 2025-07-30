package onecenter.com.br.ecommerce.pedidos.exception.pagamento;

import onecenter.com.br.ecommerce.utils.Constantes;

public class AtualizarStatusEstornoPagamento extends RuntimeException{

    public AtualizarStatusEstornoPagamento(){
        super(Constantes.ErroAtualizarStatusEstorno);
    }
}
