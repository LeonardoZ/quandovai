package br.com.quandovai.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.quandovai.modelo.entidade.Cliente;

public class PreparoEnvio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(min = 3)
    private String conteudo;

    @NotNull
    private Provedor provedor;

    @Min(1)
    private Integer quantidade;

    @NotNull
    private Periodo periodo;

    @NotNull
    @Future
    private LocalDateTime dataHoraBase;

    private List<Long> idsClientes;
    
    private List<Cliente> destinatarios;
    
    public PreparoEnvio() {
	idsClientes = new LinkedList<>();
	destinatarios = new LinkedList<>();
	periodo = Periodo.DIARIO;
	quantidade = 1;
    }
    
    public boolean ehUnicoEnvio(){
	return ehUnicoCliente() && ehUnicaData();
    }
    
    public boolean ehUnicoCliente(){
	return destinatarios != null && destinatarios.size() == 1;
    }
    
    public boolean ehUnicaData(){
	return periodo == null || quantidade == 1;
    }

    public String getConteudo() {
	return conteudo;
    }

    public void setConteudo(String conteudo) {
	this.conteudo = conteudo;
    }

    public Provedor getProvedor() {
	return provedor;
    }

    public void setProvedor(Provedor provedor) {
	this.provedor = provedor;
    }

    public Integer getQuantidade() {
	return quantidade;
    }

    public void setQuantidade(int quantidade) {
	this.quantidade = quantidade == 0 ? 1 : quantidade;
    }

    public Periodo getPeriodo() {
	return periodo;
    }

    public void setPeriodo(Periodo periodo) {
	this.periodo = periodo == null ? Periodo.DIARIO : periodo;
    }

    public LocalDateTime getDataHoraBase() {
	return dataHoraBase;
    }

    public void setDataHoraBase(LocalDateTime dataHoraBase) {
	this.dataHoraBase = dataHoraBase;
    }
    
    public List<Long> getIdsClientes() {
	return idsClientes;
    }
    
    public void setIdsClientes(List<Long> idsClientes) {
	this.idsClientes = idsClientes;
    }

    public List<Cliente> getDestinatarios() {
	return destinatarios;
    }

    public void setDestinatarios(List<Cliente> destinatarios) {
	this.destinatarios = destinatarios;
    }


    public Cliente primeiroCliente() {
	return destinatarios.get(0);
    }
    

}
