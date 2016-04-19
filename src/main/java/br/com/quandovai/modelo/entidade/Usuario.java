package br.com.quandovai.modelo.entidade;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "usuarios")
@NamedQueries(@NamedQuery(name = "Usuario.buscaPorEmail", query = "select u from Usuario u where u.email = :email"))
@Where(clause = "deletado = 0")
@SQLDelete(sql = "update usuarios set deletado = 1 where id = ?")
public class Usuario extends Entidade {

	private static final long serialVersionUID = 1L;

	@Column(name = "nome", length = 200, nullable = false)
	private String nome;

	@Column(name = "email", length = 200, nullable = false)
	private String email;

	@Column(columnDefinition = "binary(20)", nullable = false, name = "senha_hash")
	private byte[] senhaHash;

	@Column(columnDefinition = "binary(8)", nullable = false)
	private byte[] salt;

	@ElementCollection
	@Enumerated(EnumType.STRING)
	private Set<Papel> papeis;

	public Usuario() {
		papeis = new HashSet<>();
	}

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

	public Set<Papel> getPapeis() {
		return papeis;
	}

	public void setPapeis(Set<Papel> papeis) {
		this.papeis = papeis;
	}

	public byte[] getSenhaHash() {
		return senhaHash;
	}

	public void setSenhaHash(byte[] senhaHash) {
		this.senhaHash = senhaHash;
	}

	public byte[] getSalt() {
		return salt;
	}

	public void setSalt(byte[] salt) {
		this.salt = salt;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Usuario)) {
			return false;
		}
		Usuario castOther = (Usuario) other;
		return new EqualsBuilder().append(nome, castOther.nome).append(email, castOther.email)
				.append(senhaHash, castOther.senhaHash).append(salt, castOther.salt).append(papeis, castOther.papeis)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(nome).append(email).append(senhaHash).append(salt).append(papeis)
				.toHashCode();
	}

}
