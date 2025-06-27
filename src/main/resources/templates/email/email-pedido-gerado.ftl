<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Pedido Realizado</title>
</head>
<body style="font-family: Arial, sans-serif;">
    <h2>PEDIDO REALIZADO</h2>
    <p>Olá, ${nomeRazaosocial}!<p>
    <p>Seu pedido <strong>#${pedido}</strong> foi realizado.</strong>.</p>
    <p>Assim que o pagamento for aprovado, enviaremos atualizações sobre a entrega.</p>
    <h3>Confira abaixo os detalhes do seu pedido </h3>
    <p><strong>${statusPedido?lower_case?replace("_", " ")}</p>

    <h3>📦 Itens do Pedido</h3>
    <#list itensPedidos as item>
        <div style="border: 1px solid #ccc; padding: 10px; margin-bottom: 10px;">
        <#if item.produtos.produtoImagem??>
            <img src="${item.produtos.produtoImagem}" alt="Imagem do produto" width="100" height="100" style="float: left; margin-right: 15px;">
        </#if>
            <p><strong>${item.produtos.nome}</strong></p>
            <p>Quantidade: ${item.quantidade}</p>
            <p>Preço: R$ ${item.precoUnitario}</p>
            <div style="clear: both;"></div>
        </div>
    </#list>

    <h3>💰 Resumo do Pedido</h3>
    <#if cupomDesconto?? && cupomDesconto?has_content>
        <p><strong>Cupom aplicado: </strong> ${cupomDesconto}</p>
    <#else>
        <p><strong>Cupom aplicado: -R$0,00.</strong></p>
    </#if>
    <p><strong>Valor Total: R$ ${valorTotal}</strong></p>

    <h3>📍 Endereço de Entrega</h3>
    <p><strong>Nome do Destinatario: </strong> ${nomeRazaosocial}<br>
    <strong>Número de  telefone: </strong> ${telefone}<br>
    <a href="https://www.google.com/maps/search/?api=1&query=${rua} ${numero}, ${bairro}, ${cidade}, ${estado}, ${cep}" target="_blank">
            ${rua}, ${numero} - ${bairro}, ${cidade} - ${estado} - ${cep}
    </a>
    <br><br>
    <p style="margin-top: 30px;">Obrigado por comprar com a <strong>One Center</strong>!</p>
</body>
</html>
