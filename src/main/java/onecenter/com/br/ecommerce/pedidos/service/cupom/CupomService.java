package onecenter.com.br.ecommerce.pedidos.service.cupom;

import onecenter.com.br.ecommerce.pedidos.dto.request.CupomRequest;
import onecenter.com.br.ecommerce.pedidos.dto.response.CupomResponse;
import onecenter.com.br.ecommerce.pedidos.entity.CupomEntity;
import onecenter.com.br.ecommerce.pedidos.exception.CupomException;
import onecenter.com.br.ecommerce.pedidos.exception.PedidosException;
import onecenter.com.br.ecommerce.pedidos.repository.cupom.ICupomRepository;
import onecenter.com.br.ecommerce.utils.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CupomService {

    @Autowired
    private ICupomRepository iCupomRepository;

    public static final Logger logger = LoggerFactory.getLogger(CupomService.class);

    @Transactional
    public CupomResponse cadastrarCupom(CupomRequest cupom){
        try {
            CupomEntity cupomCriado = CupomEntity.builder()
                    .codigo(cupom.getCupom())
                    .valorDesconto(cupom.getValorDesconto())
                    .dataValidade(cupom.getDataValidade())
                    .build();
            CupomEntity cupomGerado = iCupomRepository.cadastrarCupom(cupomCriado);
            return mapearCupom(cupomGerado);
        }
        catch (Exception e){
            logger.error(Constantes.ErroRegistrarNoServidor);
            throw new CupomException();
        }
    }

    private CupomResponse mapearCupom(CupomEntity cupom){
        return CupomResponse.builder()
                .cupom(cupom.getCodigo())
                .valorDesconto(cupom.getValorDesconto())
                .dataValidade(cupom.getDataValidade())
                .build();
    }
}
