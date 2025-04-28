package onecenter.com.br.ecommerce.pedidos.dto.mapper;

import lombok.RequiredArgsConstructor;
import onecenter.com.br.ecommerce.pedidos.dto.response.PedidoResponse;
import onecenter.com.br.ecommerce.pedidos.entity.PedidosEntity;
import onecenter.com.br.ecommerce.pessoa.dto.mapper.EnderecoDtoMapper;
import onecenter.com.br.ecommerce.pessoa.dto.pessoas.response.PessoaResponse;
import onecenter.com.br.ecommerce.pessoa.entity.PessoaEntity;
import onecenter.com.br.ecommerce.pessoa.entity.endereco.EnderecoEntity;
import onecenter.com.br.ecommerce.pessoa.repository.endereco.IEnderecoRepository;
import onecenter.com.br.ecommerce.pessoa.repository.pessoas.IPessoaRepository;
import onecenter.com.br.ecommerce.produto.entity.produtos.ProdutosEntity;
import onecenter.com.br.ecommerce.produto.repository.produtos.IProdutosRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PedidoDtoMapper {

    private final IProdutosRepository iProdutosRepository;
    private final IPessoaRepository iPessoaRepository;
    private final IEnderecoRepository iEnderecoRepository;
    private final EnderecoDtoMapper enderecoDtoMapper;

    public PedidoResponse mapear(PedidosEntity pedido){
        ProdutosEntity produto = iProdutosRepository.buscarIdProduto(pedido.getIdProduto());
        PessoaEntity pessoa = iPessoaRepository.buscarIdPessoa(pedido.getCliente().getIdPessoa());
        EnderecoEntity endereco = iEnderecoRepository.obterEnderecoPorIdPessoa(pessoa.getIdPessoa());

        return PedidoResponse.builder()
                .idPedido(pedido.getIdPedido())
                .quantidade(pedido.getQuantidade())
                .dataPedido(pedido.getDataPedido())
                .statusPedido(pedido.getStatusPedido())
                .idProduto(produto.getIdProduto())
                .nome(produto.getNome())
                .descricaoProduto(produto.getDescricaoProduto())
                .preco(produto.getPreco())
                .produtoImagem(produto.getProdutoImagem())
                .nomeCategoria(produto.getNomeCategoria())
                .cliente(mapearPessoa(pessoa, endereco))
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
}
