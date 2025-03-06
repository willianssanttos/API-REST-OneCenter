package onecenter.com.br.ecommerce.pedidos.exception;

import onecenter.com.br.ecommerce.utils.Constantes;

public class ErroAoLocalizarPedidoNotFoundException extends RuntimeException{

    public ErroAoLocalizarPedidoNotFoundException(){
        super(Constantes.ErroLocalizarPedido);
    }
}
