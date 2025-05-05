package onecenter.com.br.ecommerce.pedidos.dto.mapper;

import lombok.RequiredArgsConstructor;
import onecenter.com.br.ecommerce.pedidos.dto.response.ItemPedidoResponse;
import onecenter.com.br.ecommerce.pedidos.dto.response.PedidoResponse;
import onecenter.com.br.ecommerce.pedidos.entity.ItemPedidoEntity;
import onecenter.com.br.ecommerce.pedidos.entity.PedidoEntity;
import onecenter.com.br.ecommerce.pedidos.repository.itemPedido.IItemsPedidoRepository;
import onecenter.com.br.ecommerce.pessoa.dto.mapper.EnderecoDtoMapper;
import onecenter.com.br.ecommerce.pessoa.dto.pessoas.response.PessoaResponse;
import onecenter.com.br.ecommerce.pessoa.entity.PessoaEntity;
import onecenter.com.br.ecommerce.pessoa.entity.endereco.EnderecoEntity;
import onecenter.com.br.ecommerce.pessoa.repository.endereco.IEnderecoRepository;
import onecenter.com.br.ecommerce.pessoa.repository.pessoas.IPessoaRepository;
import onecenter.com.br.ecommerce.produto.entity.produtos.ProdutosEntity;
import onecenter.com.br.ecommerce.produto.repository.produtos.IProdutosRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PedidoDtoMapper {

    private final IProdutosRepository iProdutosRepository;
    private final IPessoaRepository iPessoaRepository;
    private final IEnderecoRepository iEnderecoRepository;
    private final EnderecoDtoMapper enderecoDtoMapper;
    private final IItemsPedidoRepository iItemsPedidoRepository;

    public PedidoResponse mapear(PedidoEntity pedido){

        PessoaEntity pessoa = iPessoaRepository.buscarIdPessoa(pedido.getCliente().getIdPessoa());
        EnderecoEntity endereco = iEnderecoRepository.obterEnderecoPorIdPessoa(pessoa.getIdPessoa());

        List<ItemPedidoResponse> itensResponse = pedido.getItens().stream()
                .map(this::mapearItemPedido)
                .collect(Collectors.toList());

        return PedidoResponse.builder()
                .idPedido(pedido.getIdPedido())
                .dataPedido(pedido.getDataPedido())
                .statusPedido(pedido.getStatusPedido())
                .descontoAplicado(pedido.getDescontoAplicado())
                .valorTotal(pedido.getValorTotal())
                .cliente(mapearPessoa(pessoa, endereco))
                .itemPedido(itensResponse)
                .build();
    }

    private PessoaResponse mapearPessoa(PessoaEntity pessoa, EnderecoEntity endereco) {
        return PessoaResponse.builder()
                .idPessoa(pessoa.getIdPessoa())
                .nomeRazaosocial(pessoa.getNomeRazaosocial())
                .email(pessoa.getEmail())
                .telefone(pessoa.getTelefone())
                .endereco(enderecoDtoMapper.mapear(endereco))
                .build();
    }

    private ItemPedidoResponse mapearItemPedido(ItemPedidoEntity itemPedido){
        ProdutosEntity produto = iProdutosRepository.buscarIdProduto(itemPedido.getProdutos().getIdProduto());
        return ItemPedidoResponse.builder()
                .quantidade(itemPedido.getQuantidade())
                .precoUnitario(itemPedido.getPrecoUnitario())
                .idProduto(produto.getIdProduto())
                .nome(produto.getNome())
                .descricaoProduto(produto.getDescricaoProduto())
                .produtoImagem(produto.getProdutoImagem())
                .nomeCategoria(produto.getNomeCategoria())
                .build();
    }
}
