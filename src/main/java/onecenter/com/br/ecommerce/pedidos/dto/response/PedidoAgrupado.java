package onecenter.com.br.ecommerce.pedidos.dto.response;

import onecenter.com.br.ecommerce.pedidos.entity.PedidoEntity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PedidoAgrupado {

    public static List<PedidoEntity> agruparPedidos(List<PedidoEntity> pedidosItens){
        Map<Integer, PedidoEntity> pedidosMap = new LinkedHashMap<>();

        for(PedidoEntity pedidosItem : pedidosItens){
            int idPedido = pedidosItem.getIdPedido();

            if(!pedidosMap.containsKey(idPedido)){
                PedidoEntity novoPedido = PedidoEntity.builder()
                        .idPedido(pedidosItem.getIdPedido())
                        .dataPedido(pedidosItem.getDataPedido())
                        .statusPedido(pedidosItem.getStatusPedido())
                        .descontoAplicado(pedidosItem.getDescontoAplicado())
                        .valorTotal(pedidosItem.getValorTotal())
                        .cliente(pedidosItem.getCliente())
                        .itens(new ArrayList<>())
                        .build();

                pedidosMap.put(idPedido, novoPedido);
            }
            pedidosMap.get(idPedido).getItens().addAll(pedidosItem.getItens());
        }
        return new ArrayList<>(pedidosMap.values());
    }
}
