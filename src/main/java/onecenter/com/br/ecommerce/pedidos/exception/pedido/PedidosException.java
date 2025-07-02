package onecenter.com.br.ecommerce.pedidos.exception.pedido;

import onecenter.com.br.ecommerce.utils.Constantes;

public class PedidosException extends RuntimeException {

    public PedidosException(){
        super(Constantes.ErroCadastrarPedido);
    }
}
