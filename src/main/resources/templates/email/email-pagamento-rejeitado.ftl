<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Pagemento Rejeitado</title>
</head>
<body style="font-family: Arial, sans-serif;">

    <h2>PAGAMENTO REJEITADO</h2>
    <p>Olá, ${nomeRazaosocial}!<p>
    <p>O pagamento dos(s) pedidos(s) <strong>#${pedido}</strong> foi rejeitado(s)!.</strong>.</p><br>
    <p>Verifique as informações com administradora financeira o ocorrido.</p><br>

    <h3>Confira abaixo os detalhes do seu pedido </h3>
    <p><strong>${statusPedido?lower_case?replace("_", " ")}</p>

    <h3>📦 Detalhes dos(s) Produtos(s)</h3>
    <#list itensPedidos as item>
        <div style="border: 1px solid #ccc; padding: 10px; margin-bottom: 10px;">
            <p>Quantidade: ${item.quantidade}</p>
            <p>Preço: R$ ${item.precoUnitario}</p>
            <div style="clear: both;"></div>
        </div>
    </#list>

    <p style="margin-top: 30px;">Obrigado por comprar com a <strong>One Center</strong>!</p>
</body>
</html>
