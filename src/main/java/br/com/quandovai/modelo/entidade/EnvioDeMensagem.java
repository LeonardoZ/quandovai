package br.com.quandovai.modelo.entidade;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import br.com.quandovai.daos.LocalDateTimeAttributeConverter;
import br.com.quandovai.modelo.Provedor;
import br.com.quandovai.modelo.StatusEntrega;

@Entity
@Table(name = "envio_mensagem")
@Where(clause = "deletado = 0")
@SQLDelete(sql = "update envio_mensagem set deletado = 1 where id = ?")
@NamedQueries({ @NamedQuery(name = "envioMensagem.ultimasDe5Minutos", query = "select e from EnvioDeMensagem e "
	+ "where (e.mensagem.dateHoraDeEnvio between :inicial and :final) "
	+ "and (e.status = :agendado or e.status = :reenviar) " + "order by e.mensagem.dateHoraDeEnvio desc")

})
public class EnvioDeMensagem extends Entidade {

    private static final long serialVersionUID = 1L;

    @Convert(converter = LocalDateTimeAttributeConverter.class)
    @Column(name = "enviado_em")
    private LocalDateTime enviadoEm;

    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    @JoinColumn(name = "mensagem_id", nullable = false)
    private Mensagem mensagem;

    @Enumerated(EnumType.STRING)
    private StatusEntrega status;

    @Enumerated(EnumType.STRING)
    private Provedor provedor;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    public EnvioDeMensagem() {

    }

    public LocalDateTime getEnviadoEm() {
	return enviadoEm;
    }

    public void setEnviadoEm(LocalDateTime enviadoEm) {
	this.enviadoEm = enviadoEm;
    }

    public Mensagem getMensagem() {
	return mensagem;
    }

    public void setMensagem(Mensagem mensagem) {
	this.mensagem = mensagem;
    }

    public StatusEntrega getStatus() {
	return status;
    }

    public void setStatus(StatusEntrega status) {
	this.status = status;
    }

    public Cliente getCliente() {
	return cliente;
    }

    public void setCliente(Cliente cliente) {
	this.cliente = cliente;
    }

    public void setProvedor(Provedor provedor) {
	this.provedor = provedor;
    }

    public Provedor getProvedor() {
	return provedor;
    }

    public void confirmarEnvio() {
	setEnviadoEm(LocalDateTime.now());
	setStatus(StatusEntrega.ENVIADO);
    }

    public static EnvioDeMensagem criarEnvio(Cliente cliente, Mensagem mensagem,Provedor provedor) {
	EnvioDeMensagem envio = new EnvioDeMensagem();
	envio.setCliente(cliente);
	envio.setMensagem(mensagem);
	envio.setStatus(StatusEntrega.AGENDADO);
	envio.setProvedor(provedor);
	return envio;
    }

    @Override
    public boolean equals(final Object other) {
	if (!(other instanceof EnvioDeMensagem)) {
	    return false;
	}
	EnvioDeMensagem castOther = (EnvioDeMensagem) other;
	return new EqualsBuilder().append(enviadoEm, castOther.enviadoEm).append(mensagem, castOther.mensagem)
		.append(status, castOther.status).append(cliente, castOther.cliente).isEquals();
    }

    @Override
    public int hashCode() {
	return new HashCodeBuilder().append(enviadoEm).append(mensagem).append(status).append(cliente).toHashCode();
    }

}
