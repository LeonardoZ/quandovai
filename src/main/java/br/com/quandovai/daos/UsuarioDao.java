package br.com.quandovai.daos;

import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import br.com.quandovai.modelo.entidade.Papel;
import br.com.quandovai.modelo.entidade.Usuario;

public class UsuarioDao {

    private GenericDao<Usuario> dao;

    @Inject
    public UsuarioDao(GenericDao<Usuario> dao) {
	this.dao = dao;
	this.dao.configuraDao(Usuario.class);
    }

    public void salvar(Usuario t) {
	dao.salvar(t);
    }

    public Usuario buscaPorId(Long id) {
	return dao.buscaPorId(id);
    }

    public Set<String> permissoesPorPapel(String role) {
	return Papel.qual(role).listarPermissoes();
    }

    public Set<String> papeisPorEmail(String email) {
	return buscaPorEmail(email).getPapeis().stream().map(Papel::name).collect(Collectors.toSet());
    }

    public Usuario buscaPorEmail(String email) {
	try {
	    return dao.getManager().createNamedQuery("Usuario.buscaPorEmail", Usuario.class)
		    .setParameter("email", email).getSingleResult();
	} catch (NoResultException nre) {
	    return null;
	}
    }
}
