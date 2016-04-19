package br.com.quandovai.modelo.entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Email;

@Entity
@Table(name = "clientes")
@Where(clause = "deletado = 0")
@SQLDelete(sql = "update clientes set deletado = 1 where id = ?")
@NamedQueries({
		@NamedQuery(name = "Cliente.buscaPorNomeParcial", query = "select c from Cliente c where c.nomeCompleto like :nome"),
		@NamedQuery(name = "Cliente.countPorNomeParcial", query = "select count(c) from Cliente c where c.nomeCompleto like :nome") })
public class Cliente extends Entidade {

	private static final long serialVersionUID = 1L;

	@Column(name = "nome_completo", length = 150)
	private String nomeCompleto;

	@Enumerated(EnumType.STRING)
	private Sexo sexo;

	@Past
	@Temporal(TemporalType.DATE)
	@Column(name = "data_aniversario")
	private Date dataAniversario;

	@Email
	@Column(length = 120)
	private String email;

	@Column(length = 20)
	private String celular;

	@Column(length = 20)
	private String celular2;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	public Cliente() {

	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public Date getDataAniversario() {
		return dataAniversario;
	}

	public void setDataAniversario(Date dataAniversario) {
		this.dataAniversario = dataAniversario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getCelular2() {
		return celular2;
	}

	public void setCelular2(String celular2) {
		this.celular2 = celular2;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Cliente)) {
			return false;
		}
		Cliente castOther = (Cliente) other;
		return new EqualsBuilder().append(nomeCompleto, castOther.nomeCompleto).append(sexo, castOther.sexo)
				.append(dataAniversario, castOther.dataAniversario).append(email, castOther.email)
				.append(celular, castOther.celular).append(celular2, castOther.celular2).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(nomeCompleto).append(sexo).append(dataAniversario).append(email)
				.append(celular).append(celular2).toHashCode();
	}

}
