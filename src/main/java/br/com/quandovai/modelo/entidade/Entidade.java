package br.com.quandovai.modelo.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Where;

@MappedSuperclass
@Where(clause = "deletado = 0")
public class Entidade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "deletado")
	private boolean deletado;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Entidade)) {
			return false;
		}
		Entidade castOther = (Entidade) other;
		return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}

}
