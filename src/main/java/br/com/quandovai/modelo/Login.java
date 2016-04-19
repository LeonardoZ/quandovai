package br.com.quandovai.modelo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Email;

public class Login {

	@NotNull
	@Email
	private String email;

	@NotNull
	@Size(min = 6, max = 8)
	private String senha;

	public Login() {

	}

	public Login(String email, String senha) {
		super();
		this.email = email;
		this.senha = senha;
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

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Login)) {
			return false;
		}
		Login castOther = (Login) other;
		return new EqualsBuilder().append(email, castOther.email).append(senha, castOther.senha).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(email).append(senha).toHashCode();
	}

}
