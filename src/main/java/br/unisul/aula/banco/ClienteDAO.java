package br.unisul.aula.banco;

import br.unisul.aula.dtocliente.ClienteDTO;
import br.unisul.aula.modelo.Cliente;
import br.unisul.aula.modelo.Endereco;
import br.unisul.aula.modelo.UnidadeFederativa;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ClienteDAO {

    public ClienteDAO() {
        List<Cliente> clientes = this.findAll();
        if (clientes.size() > 0) clientes.forEach(c -> this.remove(c.getId()));
        Endereco endereco = new Endereco();
        endereco.setCidade("Desterro");
        endereco.setUf(UnidadeFederativa.SC);
        Cliente cliente1 = new Cliente();
        cliente1.setNome("Rodrigo");
        cliente1.setEndereco(endereco);
        Cliente cliente2 = new Cliente();
        cliente2.setNome("João");
        cliente2.setEndereco(endereco);
        this.insert(cliente1);
        this.insert(cliente2);
    }

    public void insert(Cliente cliente) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(entityManager.contains(cliente) ? cliente : entityManager.merge(cliente));
        entityManager.getTransaction().commit();
    }

    public void remove(Long id) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        entityManager.getTransaction().begin();
        Cliente cliente = findById(id);
        // https://stackoverflow.com/questions/17027398/java-lang-illegalargumentexception-removing-a-detached-instance-com-test-user5
        entityManager.remove(entityManager.contains(cliente) ? cliente : entityManager.merge(cliente));
        entityManager.getTransaction().commit();
    }

    public void update(Cliente cliente) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(cliente);
        entityManager.getTransaction().commit();
    }

    public List<Cliente> findAll() {
        EntityManager entityManager = JPAUtil.getEntityManager();

        return entityManager
                .createQuery("SELECT s FROM Cliente s", Cliente.class)
                .getResultList();
    }

    public Cliente findById(Long id) {
        EntityManager entityManager = JPAUtil.getEntityManager();
            TypedQuery<Cliente> query = entityManager
                .createQuery("SELECT s FROM Cliente s where s.id =:id", Cliente.class);
        return query.setParameter("id", id).getSingleResult();
    }

    public List<Cliente> findByCidade(String cidade){
        EntityManager entityManager = JPAUtil.getEntityManager();
        TypedQuery<Cliente> query =
                entityManager.createQuery("SELECT c FROM Cliente c where c.endereco.cidade = :cidade",
                        Cliente.class);
        return query.setParameter("cidade", cidade).getResultList();
    }
}
