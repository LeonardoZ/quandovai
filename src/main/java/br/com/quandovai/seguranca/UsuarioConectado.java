package br.com.quandovai.seguranca;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;

@SessionScoped
public class UsuarioConectado implements Serializable {

    private static final long serialVersionUID = 1L;
    private String email;
    private String nome;
    private boolean estaLogado;

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getPrimeiroNome() {
	String[] separadoPorEspaco = nome.split(" ");
	return separadoPorEspaco.length > 0 ? separadoPorEspaco[0] : nome;
    }

    public void setNome(String nome) {
	this.nome = nome;
    }

    public boolean estaLogado() {
	return estaLogado;
    }

    public void confirmaLogado() {
	this.estaLogado = true;
    }

    public void deslogar() {
	this.estaLogado = false;
    }

}
