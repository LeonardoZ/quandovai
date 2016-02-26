package br.com.quandovai.modelo.entidade;

import java.util.Set;

import com.google.common.collect.Sets;

public enum Papel {

    USUARIO(new String[] { "CLIENTE", "MENSAGENS", "CONFIGURAR" });

    private String[] permissoes;

    private Papel(String[] permissoes) {
	this.permissoes = permissoes;
    }

    public String[] getPermissoes() {
	return permissoes;
    }

    public Set<String> listarPermissoes() {
	return Sets.newHashSet(permissoes);
    }

    public static Papel qual(String role) {
	return USUARIO;
    }

}
