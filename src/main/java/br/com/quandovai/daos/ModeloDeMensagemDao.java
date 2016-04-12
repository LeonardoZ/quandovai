package br.com.quandovai.daos;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.TypedQuery;

import br.com.quandovai.modelo.PaginatedList;
import br.com.quandovai.modelo.entidade.ModeloDeMensagem;

public class ModeloDeMensagemDao {

    private GenericDao<ModeloDeMensagem> dao;

    @Inject
    public ModeloDeMensagemDao(GenericDao<ModeloDeMensagem> dao) {
	this.dao = dao;
	this.dao.configuraDao(ModeloDeMensagem.class);
    }

    public List<ModeloDeMensagem> todos() {
	return dao.todos();
    }

    public void salvar(ModeloDeMensagem mensagem) {
	dao.salvar(mensagem);
    }

    public ModeloDeMensagem buscaPorId(Long id) {
	return dao.buscaPorId(id);
    }

    public void remover(ModeloDeMensagem mensagem) {
	dao.remover(mensagem);
    }

    public void atualizar(ModeloDeMensagem mensagem) {
	dao.atualizar(mensagem);
    }

    public PaginatedList paginado(String busca, int page, int max) {
	TypedQuery<ModeloDeMensagem> resultados = dao.getManager().createNamedQuery("ModeloDeMensagem.buscaPorConteudo",
		ModeloDeMensagem.class);
	busca = "%" + busca + "%";
	resultados.setParameter("conteudo", busca);
	TypedQuery<Number> contagem = dao.getManager().createNamedQuery("ModeloDeMensagem.countPorConteudo", Number.class);
	contagem.setParameter("conteudo", busca);
	return dao.paginado(resultados, contagem, page, max);
    }

    public PaginatedList paginado(int page, int max) {
	return dao.paginado(page, max);
    }

}
