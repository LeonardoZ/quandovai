package br.com.quandovai.modelo.entidade;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import br.com.quandovai.daos.LocalDateTimeAttributeConverter;

@Entity
@Table(name = "mensagens")
@Where(clause = "deletado = 0")
@SQLDelete(sql = "update mensagens set deletado = 1 where id = ?")
@NamedQueries({
	@NamedQuery(name = "Mensagem.buscaPorConteudo", query = "select m from Mensagem m where m.conteudo like :conteudo"),
	@NamedQuery(name = "Mensagem.countPorConteudo", query = "select count(m) from Mensagem m where m.conteudo like :conteudo") })
public class Mensagem extends Entidade {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private String conteudo;

    @Enumerated(EnumType.STRING)
    @Column(length = 5, name = "tipo_envio")
    private TipoEnvio tipoDeEnvio;

    @Convert(converter = LocalDateTimeAttributeConverter.class)
    @Column(length = 5, name = "data_hora_envio")
    private LocalDateTime dateHoraDeEnvio;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Mensagem() {

    }

    public Mensagem(String conteudo, boolean enviado, String tipoDeEnvio, LocalDateTime dateHoraDeEnvio) {
	super();
	this.conteudo = conteudo;
	this.tipoDeEnvio = TipoEnvio.porNome(tipoDeEnvio);
	this.dateHoraDeEnvio = dateHoraDeEnvio;
    }

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

    public LocalDateTime getDateHoraDeEnvio() {
	return dateHoraDeEnvio;
    }

    public LocalDateTime horaDeEnvioAjustada() {
	return ehAntigaNaoEnviada() ? novaDataDeEnvio() : dateHoraDeEnvio;
    }

    public boolean ehAntigaNaoEnviada() {
	return dateHoraDeEnvio.isBefore(LocalDateTime.now());
    }

    public LocalDateTime novaDataDeEnvio() {
	return LocalDateTime.now().plus(1, ChronoUnit.MINUTES);
    }

    public Date dateHoraDeEnvioConvertido() {
	Instant instant = dateHoraDeEnvio.toInstant(ZoneOffset.UTC);
	Date fromInstant = Date.from(instant);
	return fromInstant;
    }

    public void setDateHoraDeEnvio(LocalDateTime dateHoraDeEnvio) {
	this.dateHoraDeEnvio = dateHoraDeEnvio;
    }

    public void setDateHoraDeEnvio(Date data) {
	Instant instant = Instant.ofEpochMilli(data.getTime());
	this.dateHoraDeEnvio = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public Usuario getUsuario() {
	return usuario;
    }

    public void setUsuario(Usuario usuario) {
	this.usuario = usuario;
    }

    @Override
    public String toString() {
	return "Mensagem [id=" + getId() + ", conteudo=" + conteudo + " tipoDeEnvio=" + tipoDeEnvio
		+ ", dateHoraDeEnvio=" + dateHoraDeEnvio + "]";
    }

    @Override
    public boolean equals(final Object other) {
	if (!(other instanceof Mensagem)) {
	    return false;
	}
	Mensagem castOther = (Mensagem) other;
	return new EqualsBuilder().append(conteudo, castOther.conteudo).append(tipoDeEnvio, castOther.tipoDeEnvio)
		.append(dateHoraDeEnvio, castOther.dateHoraDeEnvio).append(usuario, castOther.usuario).isEquals();
    }

    @Override
    public int hashCode() {
	return new HashCodeBuilder().append(conteudo).append(tipoDeEnvio).append(dateHoraDeEnvio).append(usuario)
		.toHashCode();
    }

}
