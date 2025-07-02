<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Pedido a Caminho</title>
</head>
<body style="font-family: Arial, sans-serif;">

    <h2>PEDIDO A CAMINHO</h2>
    <p>Olá, ${nomeRazaosocial}!<p>
    <p>O seu pedidos <strong>#${pedido}</strong> já está a caminho! Se quiser acompanhar o status de entrega de seus produtos. clieque aqui.</strong>.</p><br>
    <p>Por favor, quando o seus produtos chegarem, verifique se está tudo certo com o pedido e confirme o recebimento.</p><br>
    <p>Lembrando: a One Center nunca fará cobranças adicionais após sua compra.</p><br>
    <p>Voce pode acompanhar a entrega na aba Meus Pedidos e acessar sua nota clicando abaixo.</p><br>

    <h3>Confira abaixo os detalhes do seu pedido </h3>
    <p><strong>${statusPedido?lower_case?replace("_", " ")}</p>

    <h3>📦 Detalhes dos(s) Produtos(s)</h3>
    <#list itensPedidos as item>
        <div style="border: 1px solid #ccc; padding: 10px; margin-bottom: 10px;">
            <img src="${item.produtos.produtoImagem}" alt="Imagem do produto" width="100" height="100" style="float: left; margin-right: 15px;">
            <p><strong>${item.produtos.nome}</strong></p>
            <p>Quantidade: ${item.quantidade}</p>
            <p>Entrega: R$ ${item.precoUnitario}</p>
            <div style="clear: both;"></div>
        </div>
    </#list>


    <h3>📍 Detalhes da Entrega</h3>
    <p><strong>Nome do Destinatario: </strong> ${nomeRazaosocial}<br>
    <strong>Número de  telefone: </strong> ${telefone}<br>
    <a href="https://www.google.com/maps/search/?api=1&query=${rua} ${numero}, ${bairro}, ${cidade}, ${estado}, ${cep}" target="_blank">
            ${rua}, ${numero} - ${bairro}, ${cidade} - ${estado} - ${cep}
    </a>
    <br><br>
    <p style="margin-top: 30px;">Abraços,
     <strong>One Center</strong>!</p>
</body>
</html>
