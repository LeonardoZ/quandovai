package br.com.quandovai.daos;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.TypedQuery;

import br.com.quandovai.modelo.PaginatedList;
import br.com.quandovai.modelo.entidade.Mensagem;

public class MensagemDao {

	private GenericDao<Mensagem> dao;

	@Inject
	public MensagemDao(GenericDao<Mensagem> dao) {
		this.dao = dao;
		this.dao.configuraDao(Mensagem.class);
	}

	public List<Mensagem> todos() {
		return dao.todos();
	}

	public void salvar(Mensagem mensagem) {
		dao.salvar(mensagem);
	}

	public Mensagem buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public void remover(Mensagem mensagem) {
		dao.remover(mensagem);
	}

	public void atualizar(Mensagem mensagem) {
		dao.atualizar(mensagem);
	}

	public PaginatedList paginado(String busca, int page, int max) {
		TypedQuery<Mensagem> resultados = dao.getManager().createNamedQuery("Mensagem.buscaPorConteudo",
				Mensagem.class);
		busca = "%" + busca + "%";
		resultados.setParameter("nome", busca);
		TypedQuery<Number> contagem = dao.getManager().createNamedQuery("Mensagem.countPorConteudo", Number.class);
		contagem.setParameter("nome", busca);
		return dao.paginado(resultados, contagem, page, max);
	}

	public PaginatedList paginado(int page, int max) {
		return dao.paginado(page, max);
	}

}
