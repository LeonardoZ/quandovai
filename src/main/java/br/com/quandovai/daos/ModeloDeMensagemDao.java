package br.com.quandovai.daos;

import java.util.List;

import javax.inject.Inject;

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

    public PaginatedList paginado(int page, int max) {
	return dao.paginado(page, max);
    }

}
