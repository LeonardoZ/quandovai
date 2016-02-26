package br.com.quandovai.modelo;

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
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import br.com.quandovai.daos.LocalDateTimeAttributeConverter;
import br.com.quandovai.modelo.entidade.Entidade;
import br.com.quandovai.modelo.entidade.TipoEnvio;

@Entity
@Table(name = "mensagens")
@Where(clause = "deletado = 0")
@SQLDelete(sql = "update mensagens set deletado = 1 where id = ?")
public class Mensagem extends Entidade {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private String conteudo;

    @Column
    private boolean enviado;

    @Enumerated(EnumType.STRING)
    @Column(length = 5, name = "tipo_envio")
    private TipoEnvio tipoDeEnvio;

    @Convert(converter = LocalDateTimeAttributeConverter.class)
    @Column(length = 5, name = "data_hora_envio")
    private LocalDateTime dateHoraDeEnvio;

    public Mensagem() {

    }

    public Mensagem(String conteudo, boolean enviado, String tipoDeEnvio, LocalDateTime dateHoraDeEnvio) {
	super();
	this.conteudo = conteudo;
	this.enviado = enviado;
	this.tipoDeEnvio = TipoEnvio.porNome(tipoDeEnvio);
	this.dateHoraDeEnvio = dateHoraDeEnvio;
    }

    public String getConteudo() {
	return conteudo;
    }

    public void setConteudo(String conteudo) {
	this.conteudo = conteudo;
    }

    public boolean isEnviado() {
	return enviado;
    }

    public void setEnviado(boolean enviado) {
	this.enviado = enviado;
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

    public boolean ehAntigaNaoEnviada() {
	return dateHoraDeEnvio.isBefore(LocalDateTime.now());
    }

    public Date novaDataDeEnvio() {
	LocalDateTime plus = LocalDateTime.now().plus(1, ChronoUnit.MINUTES);
	Instant instant = plus.toInstant(ZoneOffset.UTC);
	Date fromInstant = Date.from(instant);
	return fromInstant;
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

    @Override
    public String toString() {
	return "Mensagem [id=" + getId() + ", conteudo=" + conteudo + ", enviado=" + enviado + ", tipoDeEnvio="
		+ tipoDeEnvio + ", dateHoraDeEnvio=" + dateHoraDeEnvio + "]";
    }

}
