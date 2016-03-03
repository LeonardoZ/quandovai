package br.com.quandovai.daos;

import java.util.List;

import javax.inject.Inject;

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

    public PaginatedList paginado(int page, int max) {
	return dao.paginado(page, max);
    }

}
