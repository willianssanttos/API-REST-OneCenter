package onecenter.com.br.ecommerce.pedidos.exception.pedido;

import onecenter.com.br.ecommerce.utils.Constantes;

public class CancelarPedidoException extends RuntimeException{

    public CancelarPedidoException(){
        super(Constantes.ErroCancelarPedido);
    }
}
