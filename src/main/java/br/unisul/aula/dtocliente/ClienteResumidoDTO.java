package br.unisul.aula.dtocliente;

import br.unisul.aula.banco.EnderecoDAO;
import br.unisul.aula.modelo.Cliente;
import br.unisul.aula.modelo.Endereco;
import br.unisul.aula.modelo.UnidadeFederativa;
import lombok.Data;

@Data
public class ClienteResumidoDTO {
    private Long id;
    private String nome;

    public ClienteResumidoDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
    }
}
