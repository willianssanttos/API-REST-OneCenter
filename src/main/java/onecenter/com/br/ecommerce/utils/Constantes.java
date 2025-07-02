package onecenter.com.br.ecommerce.utils;

public class Constantes {

    //Login
    public static final String AcessoNegadoException = "Você não tem permissão de acesso!";
    public static final String AcessoNegadoConteudoException = "Cliente não autorizado a acessar este contéudo. Cliente logado: {}";




    //Mensagem debug logger
    public static final String DebugRegistroProcesso = "Iniciando o processo de registro no servidor. ";
    public static final String DebugBuscarProcesso = "Iniciando o processo de busca no servidor.";
    public static final String DebugEditarProcesso = "Iniciando o processo de edição do registro no servidor.";
    public static final String DebugDeletarProcesso = "Iniciando o processo de deletar registro no servidor.";

    //Mensagens info logger

    public static final String InfoRegistrar = "Dados do produto '{}' salvos com sucesso.";
    public static final String InfoBuscar = "Dados do produto '{}' recuperados com sucesso.";
    public static final String InfoEditar = "Dados do produto '{}' atualizados no servidor com sucesso.";
    public static final String InfoDeletar = "Dados do produto '{}' deletado do servidor com sucesso.";


    //Mensagens de erro do servidor
    public static final String ErroRegistrarNoServidor = "Erro ao salvar o produto '{}'. Motivo: {}";

    public static final String ErroBuscarRegistroNoServidor = "Erro ao realizar buscar do registro '{}' no servidor! Motivo: {}";
    public static final String ErroEditarRegistroNoServidor = "Erro ao atualizar registro '{}' no servidor! Motivo: {}";
    public static final String ErroDeletarRegistroNoServidor = "Erro ao deletar registro '{}' no servidor! Motivo: {}";


    //Mensagem de erro de produtos
    public static final String ErroCadastrarProduto = "Não foi possivel cadastrar o produto! Tente novamente mais tarde.";
    public static final String ErroBuscarProduto = "Produtos não encontrados! Tente novamente mais tarde.";
    public static final String ErroDeletarProduto = "Erro ao deletar o produto! Tente novamente mais tarde.";
    public static final String ErroEditarProduto = "Erro ao realizar alteração das informações do produto! Tente novamente mais tarde.";



    //Mensagem de erro categoria de produtos
    public static final String ErroCadastrarCategoria = "Erro ao cadastrar Categoria! Tente novamente mais tarde.";
    public static final String ErroBuscarCategoria = "Categoria não encontrada!";


    //Mensagem de erro de imagem produto
    public static final String ErroBuscarImagemProduto = "Imagem do produto não encontrado!";


    //Mensagem validação

    public static final String cadastroNome = "Nome invalido! O nome deve conter somente letras, com no minimo 2 e no máximo 100 caracteres!";
    public static final String cadastroEmail = "Esse E-mail é inválido! Verifique o E-mail digitado.";
    public static final String cadastroCelular = "Insira o número de celular ou número fixo com DDD(dois digitos, ex: 11912341234)";
    public static final String cadastroSenha = "Senha inválida. A senha deve ter no mínimo 8 caracteres, contendo letras, números e pelo menos um caractere especial (@, #, %, &, $).";
    public static final String EmailJaCadastrado = "Esse e-mail já foi cadastrado!";
    public static final String CPFJaCadastrado = "Esse CPF já foi cadastrado!";
    public static final String CpfInvalido = "Esse CPF é invalido! Verifique o número digitado!";
    public static final String CnpjJaCadastrado = "Esse CNPJ já foi cadastrado!";
    public static final String CNPJInvalido = "Esse CNPJ é invalido! Verifique o número digitado!";
    public static final String CepInvalido = "Esse CEP é inválido! Verifique o número digitado!";
    public static final String DataNascimentoInvalida = "Verifique a Data de nascimento, a mesma esta inválida!";
    public static final String ErroCadastrarEndereco = "Erro ao registrar o endereço informado!";
    public static final String ErroEnderecoNaoEncontrado = "Esse endereço, não foi encontrado com o cep informado!";
    public static final String GerarToken = "Erro ao gerar token.";
    public static final String RolesInvalido = "Nivel de acesso invalido!(Defina como ADMINISTRADOR, MODERADOR OU CLIENTE).";
    public static final String GToken = "Token ausente.";
    public static final String Token = "Token inválido ou expirado!";
    public static final String erroLoginConta = "Usuário ou senha incorretos!";

    public static final String ERRO_ENVIAR_EMAIL = "Erro ao enviar e-mail:";



    // Mensagem de erro de pessoas
    public static final String ErroCadastrarPessoa = "Erro ao realizar o cadastro de usuário! Tente novamente mais tarde.";
    public static final String ErroBuscarCpfPessoa = "CPF não encontrado!";
    public static final String ErroBuscarCnpjPessoa = "CNPJ não encontrado!";
    public static final String ErroListaCliente = "Lista de cliente não encontrada!";
    public static final String ErroEditarPessoa = "Não foi possivel editar os dados! Tente novamente mais tarde.";
    public static final String ErroEditarEndereco = "Não foi possivel alterar o endereço! Tente novamente mais tarde.";


    //Erros de pedidos

    public static final String ErroCadastrarPedido = "Erro ao realizar pedido! Tente novamente mais tarde.";

    public static final String ErroLocalizarPedido = "Pedidos não localizados! Tente novamente mais tarde.";

    public static final String ErroPessoaNãoEncontrada = "Não foi possivel localizar cliente!.";

    public static final String ArquivoImagemVazio = "O arquivo da imagem está vazio!.";

    public static final String ErroCadastraImagem = "Erro ao realizar o cadastro da imagem! Tente novamente mais tarde.";

    public static final String ErroLocalizarImagems = "Não foi possivel localizar imagem!.";

    public static final String ErroItemPedidoNãoRegistrado = "Itens selecionados não registrado! Tente novamente mais tarde.";

    //Cupom
    public static final String ErroCadastrarCupom = "Erro ao gerar Cupom! Tente novamente mais tarde.";
    public static final String CupomInvalido = "Cupom inválido ou expirado.";
    public static final String CupomNaoEncontrado = "Cupom não encontrado!";
    public static final String CupomJaUilizado = "Cupom já foi utilizado!";
    public static final String ErroLocalizarInformacaoCupom = "Informação de cupom informado não encontrado!";
    public static final String ErroAtualizaStatusCupom = "Não foi possivel atualizar o status do cupom!";

    //Pagamentos

    public static final String PagamentoDuplicado = "Pagamento {} já processado. Ignorando duplicata!";
    public static final String ErroAoSalvarPagamento = "Não foi possivel registrar pagamento realizado!";
    public static final String ErroAoGerarCheckoutDePagamento = "Não foi possivel gerar o chechout pagamento!";
    public static final String ErroAtualizarStatusPagamentoPedido = "Não foi possivel atualizar o status do pagamento do pedido!";
    public static final String ErroAoLocalizarPagamentoExistente = "Não foi possivel localizar registro de pagamento existente na base de dados!";

    //Notificação WebHook

    public static final String NotificacaoRecebida = "Recebido webhook: {}";
    public static final String ErroNotificacaoRecebida = "Erro ao processar webhook do Mercado Pago!";
    public static final String WebhookIgnorado = "Webhook ignorado. Tipo não suportado: {}";
    public static final String WebhookTipoPagamento = "Webhook do tipo payment sem campo 'data.id'. Ignorado.";






















}
