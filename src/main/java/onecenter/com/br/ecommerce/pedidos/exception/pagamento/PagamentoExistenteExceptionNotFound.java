package onecenter.com.br.ecommerce.pedidos.exception.pagamento;

import onecenter.com.br.ecommerce.utils.Constantes;

public class PagamentoExistenteExceptionNotFound extends RuntimeException{

    public PagamentoExistenteExceptionNotFound(){
        super(Constantes.ErroAoLocalizarPagamentoExistente);
    }
}
