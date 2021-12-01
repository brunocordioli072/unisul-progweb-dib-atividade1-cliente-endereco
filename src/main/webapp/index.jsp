<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.1/jquery.js"></script>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <header class="header">
        <h1 class="header__title">Trabalho Final</h1>
        <p class="header__subtitle">Aluno: Bruno Cordioli Machado =)</p>
    </header>
    <div class="box">
        <div class="box__content">
            <label for="address">Endereço:</label><br>
            <input class="box__content__address" type="text" id="address" name="address"><br>
            <button class="box__content__submit" onclick="loadAddress()" >SUBMIT</button><br>
            <p style="color: red" id="errors"></p>
            <label>Resposta:</label><br>
            <table style="width: 100%;" id="response">
                <tr>
                    <td><b>Id</b></td>
                    <td><b>Nome</b></td>
                </tr>
            </table>
        </div>
    </div>
    <script type="text/javascript">
        function loadAddress() {
            let address = $("#address")[0].value;
            let errors = $("#errors")[0];
            let response = $('#response')[0]
            response.innerHTML = "<tr><td><b>Id</b></td> <td><b>Nome</b></td></tr>";
            errors.innerHTML = "";
            if (!address) {
                errors.innerHTML = "Endereço é obrigatório...";
                return;
            }
            const xhttp = new XMLHttpRequest();
            xhttp.onload = function() {
                const res = JSON.parse(this.responseText);
                for (let i = 0; i < res.clientes.length; i++) {
                    const c = res.clientes[i];
                    const cHtml = '<tr><td>' + c.id + '</td><td>' + c.nome + '</td></tr>';
                    $('#response').append(cHtml);
                }
            }
            xhttp.open("GET", 'http://localhost:8080/unisul_progweb_dib_atividade1_cliente_endereco_war/cliente?endereco=' + address, true);
            xhttp.send();
        }
    </script>
</body>
</html>