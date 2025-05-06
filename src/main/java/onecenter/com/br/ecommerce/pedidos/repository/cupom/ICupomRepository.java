package onecenter.com.br.ecommerce.pedidos.repository.cupom;

import onecenter.com.br.ecommerce.pedidos.entity.CupomEntity;

public interface ICupomRepository {

    CupomEntity cadastrarCupom(CupomEntity cupom);

    CupomEntity buscarCupomPorNome(String nomeCupom);

}
