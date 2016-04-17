package br.com.quandovai.modelo;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.quandovai.modelo.entidade.Cliente;
import br.com.quandovai.modelo.entidade.EnvioDeMensagem;

public class PreparoEnvio {

    @Size(min = 3)
    private String conteudo;

    @NotNull
    private Provedor provedor;

    @Min(1)
    private int quantidade;

    @NotNull
    private Periodo periodo;

    @NotNull
    @Future
    private LocalDateTime dataHoraBase;

    private List<Long> idsClientes;
    
    private List<Cliente> destinatarios;

    private List<EnvioDeMensagem> enviosProjetados;

    public PreparoEnvio() {
	idsClientes = new LinkedList<>();
	destinatarios = new LinkedList<>();
	enviosProjetados = new LinkedList<>();
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

    public int getQuantidade() {
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

    public List<EnvioDeMensagem> getEnviosProjetados() {
	return enviosProjetados;
    }

    public void setEnviosProjetados(List<EnvioDeMensagem> enviosProjetados) {
	this.enviosProjetados = enviosProjetados;
    }

    public Cliente primeiroCliente() {
	return destinatarios.get(0);
    }

}
