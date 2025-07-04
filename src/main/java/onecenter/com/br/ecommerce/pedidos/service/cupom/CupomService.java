package onecenter.com.br.ecommerce.pedidos.service.cupom;

import onecenter.com.br.ecommerce.pedidos.dto.request.CupomRequest;
import onecenter.com.br.ecommerce.pedidos.dto.response.CupomResponse;
import onecenter.com.br.ecommerce.pedidos.entity.pedido.CupomEntity;
import onecenter.com.br.ecommerce.pedidos.exception.cupom.CupomException;
import onecenter.com.br.ecommerce.pedidos.exception.cupom.CupomInvalidoException;
import onecenter.com.br.ecommerce.pedidos.exception.cupom.CupomUtilizadoPorClienteException;
import onecenter.com.br.ecommerce.pedidos.repository.cupom.ICupomRepository;
import onecenter.com.br.ecommerce.utils.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CupomService {

    @Autowired
    private ICupomRepository iCupomRepository;

    public static final Logger logger = LoggerFactory.getLogger(CupomService.class);

    @Transactional
    public CupomResponse cadastrarCupom(CupomRequest cupom, Integer token){
        logger.info(Constantes.DebugRegistroProcesso);
        try {
            CupomEntity criarCupom = CupomEntity.builder()
                    .codigo(cupom.getCupom())
                    .valorDesconto(cupom.getValorDesconto())
                    .dataValidade(cupom.getDataValidade())
                    .build();
            CupomEntity cupomGerado = iCupomRepository.cadastrarCupom(criarCupom);
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

    @Transactional
    public BigDecimal validarEAplicarCupom(String nomeCupom, Integer idPessoa){
        logger.info(Constantes.DebugBuscarProcesso);
        try {
            CupomEntity cupom = iCupomRepository.buscarCupomPorNome(nomeCupom);

            if(cupom == null && Boolean.TRUE.equals(cupom.getCupomUsado())){
                throw new CupomInvalidoException();
            }

            boolean jaUsado = iCupomRepository.cupomJaUsadoPorCliente(cupom.getIdCupom(), idPessoa);
            if (jaUsado){
                throw new CupomUtilizadoPorClienteException();
            }
            iCupomRepository.registrarUsoCupom(cupom.getIdCupom(), idPessoa);
            return cupom.getValorDesconto();
        } catch (Exception e){
            logger.error(Constantes.ErroBuscarRegistroNoServidor);
            throw new CupomException();
        }
    }

    @Transactional
    public void marcarCupomComoUsado(String nomeCupom) {
        logger.info(Constantes.DebugEditarProcesso);
        try {
            iCupomRepository.marcarCupomComoUsado(nomeCupom);
        } catch (Exception e){
            logger.error(Constantes.ErroBuscarRegistroNoServidor);
            throw new CupomException();
        }
    }
}
