package br.com.quandovai.daos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.quandovai.modelo.PaginatedList;
import br.com.quandovai.modelo.entidade.Entidade;

public class GenericDao<T extends Entidade> {

    @PersistenceContext(unitName = "default")
    private EntityManager manager;
    private Class<T> classeDoTipo;
    private String nomeDaClasse;
    private String queryTodos = "select p from %s p";

    public GenericDao() {
    }

    public void configuraDao(Class<T> classeDoTipo) {
	this.classeDoTipo = classeDoTipo;
	this.nomeDaClasse = classeDoTipo.getSimpleName();
	this.queryTodos = String.format(queryTodos, nomeDaClasse);
    }

    public EntityManager getManager() {
	return manager;
    }

    public List<T> todos() {
	return manager.createQuery(queryTodos, classeDoTipo).getResultList();
    }

    public void salvar(T t) {
	manager.persist(t);
	manager.flush();
    }

    public void salvar(Collection<T> entities) {
	final List<T> savedEntities = new ArrayList<T>(entities.size());
	int i = 0;
	for (T t : entities) {
	    savedEntities.add(salvaOuAtualiza(t));
	    i++;
	    if (i % 100 == 0) {
		// Flush a batch of inserts and release memory.
		manager.flush();
		manager.clear();
	    }
	}
    }

    private T salvaOuAtualiza(T t) {
	if (t.getId() == 0) {
	    manager.persist(t);
	    return t;
	} else {
	    return manager.merge(t);
	}
    }

    public T buscaPorId(Long id) {
	return manager.find(classeDoTipo, id);
    }

    public void remover(T t) {
	manager.remove(t);
    }

    public void atualizar(T t) {
	manager.merge(t);
    }

    public PaginatedList paginado(TypedQuery<T> query, TypedQuery<Number> countQuery, int page, int max) {
	return new PaginatorQueryHelper().list(query, countQuery, page, max);
    }

    public PaginatedList paginado(int page, int max) {
	return new PaginatorQueryHelper().list(manager, classeDoTipo, page, max);
    }

}
