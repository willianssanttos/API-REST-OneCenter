package onecenter.com.br.ecommerce.pedidos.exception.pedido;

import onecenter.com.br.ecommerce.utils.Constantes;

public class ItemPedidoException extends RuntimeException{

    public ItemPedidoException(){
        super(Constantes.ErroItemPedidoNãoRegistrado);
    }
}
