package br.com.quandovai.daos;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.TypedQuery;

import br.com.quandovai.modelo.PaginatedList;
import br.com.quandovai.modelo.entidade.Cliente;

public class ClienteDao {

	private GenericDao<Cliente> dao;

	@Inject
	public ClienteDao(GenericDao<Cliente> dao) {
		this.dao = dao;
		this.dao.configuraDao(Cliente.class);
	}

	public List<Cliente> todos() {
		return dao.todos();
	}

	public void salvar(Cliente cliente) {
		dao.salvar(cliente);
	}

	public void salvar(Collection<Cliente> clientes) {
		dao.salvar(clientes, 100);
	}

	public Cliente buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public List<Cliente> buscaPorIds(List<Long> ids) {
		return ids.stream().map(this::buscaPorId).collect(Collectors.toList());
	}

	public void remover(Cliente cliente) {
		dao.remover(cliente);
	}

	public void atualizar(Cliente cliente) {
		dao.atualizar(cliente);
	}

	public PaginatedList paginado(String busca, int page, int max) {
		TypedQuery<Cliente> resultados = dao.getManager().createNamedQuery("ModeloDeMensagem.buscaPorNomeParcial",
				Cliente.class);
		busca = "%" + busca + "%";
		resultados.setParameter("nome", busca);
		TypedQuery<Number> contagem = dao.getManager().createNamedQuery("Cliente.countPorNomeParcial", Number.class);
		contagem.setParameter("nome", busca);
		return dao.paginado(resultados, contagem, page, max);
	}

	public PaginatedList paginado(int page, int max) {
		return dao.paginado(page, max);
	}

	public List<Cliente> buscaPorNome(String nomeDoCliente) {
		return dao.getManager().createNamedQuery("Cliente.buscaPorNomeParcial", Cliente.class)
				.setParameter("nome", "%" + nomeDoCliente + "%").getResultList();
	}

}
