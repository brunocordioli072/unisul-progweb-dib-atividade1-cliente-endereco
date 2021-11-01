package br.unisul.aula.dtocliente;

import br.unisul.aula.modelo.Cliente;
import br.unisul.aula.modelo.UnidadeFederativa;
import lombok.Data;

import java.util.List;

@Data
public class ClienteByCidadeAndUfDTO {

    private String cidade;
    private UnidadeFederativa uf;
    private List<ClienteResumidoDTO> clientes;

}
