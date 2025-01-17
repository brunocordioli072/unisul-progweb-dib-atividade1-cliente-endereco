package br.unisul.aula.servlet;

import br.unisul.aula.banco.ClienteDAO;
import br.unisul.aula.dtocliente.ClienteByCidadeAndUfDTO;
import br.unisul.aula.dtocliente.ClienteDTO;
import br.unisul.aula.dtocliente.ClienteResumidoDTO;
import br.unisul.aula.modelo.Cliente;
import br.unisul.aula.modelo.UnidadeFederativa;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ClienteServletController", value = "/cliente")
public class ClienteServletController extends HttpServlet {

    private final Gson gson = new Gson();
    private final ClienteDAO clienteDAO = new ClienteDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=ISO-8859-1");
        response.setCharacterEncoding("UTF-8");
        String requestId = request.getParameter("id");
        String requestEndereco = request.getParameter("endereco");
        String clienteJson;
        if (requestId != null) {
            Long id = Long.parseLong(requestId);
            Cliente cliente = clienteDAO.findById(id);
            ClienteDTO dto = new ClienteDTO(cliente);
            clienteJson = gson.toJson(dto);
        } else if (requestEndereco != null) {
            List<Cliente> clientes = clienteDAO.findByCidade(requestEndereco);
            List<ClienteResumidoDTO> dtos = new ArrayList<>();
            clientes.forEach(c -> {
                ClienteResumidoDTO clienteResumidoDTO = new ClienteResumidoDTO(c);
                dtos.add(clienteResumidoDTO);
            });
            ClienteByCidadeAndUfDTO dto = new ClienteByCidadeAndUfDTO();
            dto.setCidade(requestEndereco);
            dto.setUf(UnidadeFederativa.SC);
            dto.setClientes(dtos);
            clienteJson = gson.toJson(dto);
        } else {
            List<Cliente> clienteList = clienteDAO.findAll();
            List<ClienteDTO> dtos = new ArrayList<>();
            for (int i = 0; i < clienteList.size(); i++) {
                ClienteDTO dto = new ClienteDTO(clienteList.get(i));
                dtos.add(dto);
            }
            clienteJson = gson.toJson(dtos);
        }
        response.getWriter().println(clienteJson);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        ClienteDTO clienteDTO = gson.fromJson(reader, ClienteDTO.class);
        Cliente cliente = clienteDTO.converterParaCliente();
        clienteDAO.insert(cliente);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        ClienteDTO clienteDTO = gson.fromJson(reader, ClienteDTO.class);
        Cliente cliente;
        if (request.getParameter("id") != null) {
            Long id = Long.parseLong(request.getParameter("id"));
            Cliente clienteAntigo = clienteDAO.findById(id);
            cliente = clienteDTO.converterParaCliente(clienteAntigo);
        } else {
            cliente = clienteDTO.converterParaCliente();
        }
        clienteDAO.update(cliente);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        Long id;
        if (request.getParameter("id") != null) {
            id = Long.parseLong(request.getParameter("id"));
        } else {
            ClienteDTO clienteDTO = gson.fromJson(reader, ClienteDTO.class);
            id = clienteDTO.getIdCliente();
        }
        clienteDAO.remove(id);
    }
}
