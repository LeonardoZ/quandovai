package br.com.quandovai.modelo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class UsuarioCadastro {

	@NotNull
	@Size(min = 3, max = 120)
	private String nome;

	@NotNull
	@Email
	private String email;

	@NotNull
	@Size(min = 6, max = 8)
	private String senha;

	@NotNull
	private String senhaNovamente;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public static UsuarioCadastro vazio() {
		return new UsuarioCadastro();
	}

	public String getSenhaNovamente() {
		return senhaNovamente;
	}

	public void setSenhaNovamente(String senhaNovamente) {
		this.senhaNovamente = senhaNovamente;
	}

	public boolean senhasDiferentes() {
		return (senha == null || senhaNovamente == null) || !senha.equals(senhaNovamente);
	}

}
