package br.com.quandovai.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import br.com.quandovai.modelo.entidade.Entidade;
import br.com.quandovai.modelo.entidade.TipoEnvio;

@Entity
@Table(name = "modelo_mensagens")
@Where(clause = "deletado = 0")
@SQLDelete(sql = "update modelo_mensagens set deletado = 1 where id = ?")
public class ModeloDeMensagem extends Entidade {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private String conteudo;

    @Enumerated(EnumType.STRING)
    @Column(length = 5, name = "tipo_envio")
    private TipoEnvio tipoDeEnvio;

    public String getConteudo() {
	return conteudo;
    }

    public void setConteudo(String conteudo) {
	this.conteudo = conteudo;
    }

    public TipoEnvio getTipoDeEnvio() {
	return tipoDeEnvio;
    }

    public void setTipoDeEnvio(TipoEnvio tipoDeEnvio) {
	this.tipoDeEnvio = tipoDeEnvio;
    }

    @Override
    public boolean equals(final Object other) {
	if (!(other instanceof ModeloDeMensagem)) {
	    return false;
	}
	ModeloDeMensagem castOther = (ModeloDeMensagem) other;
	return new EqualsBuilder().append(conteudo, castOther.conteudo).append(tipoDeEnvio, castOther.tipoDeEnvio)
		.isEquals();
    }

    @Override
    public int hashCode() {
	return new HashCodeBuilder().append(conteudo).append(tipoDeEnvio).toHashCode();
    }

}
