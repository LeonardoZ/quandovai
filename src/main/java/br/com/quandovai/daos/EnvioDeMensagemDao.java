package br.com.quandovai.daos;

import java.time.LocalDateTime;
import java.util.List;

import javax.inject.Inject;

import br.com.quandovai.modelo.PaginatedList;
import br.com.quandovai.modelo.StatusEntrega;
import br.com.quandovai.modelo.entidade.EnvioDeMensagem;

public class EnvioDeMensagemDao {

    private GenericDao<EnvioDeMensagem> dao;

    @Inject
    public EnvioDeMensagemDao(GenericDao<EnvioDeMensagem> dao) {
	this.dao = dao;
	this.dao.configuraDao(EnvioDeMensagem.class);
    }

    public List<EnvioDeMensagem> todos() {
	return dao.todos();
    }

    public void salvar(EnvioDeMensagem mensagem) {
	dao.salvar(mensagem);
    }

    public void salvar(List<EnvioDeMensagem> mensagens) {
	dao.salvar(mensagens, 10);
    }

    public EnvioDeMensagem buscaPorId(Long id) {
	return dao.buscaPorId(id);
    }

    public List<EnvioDeMensagem> ultimasMensagens(){
	// inicial - final
	// status agendado e reenviado
	LocalDateTime now = LocalDateTime.now();
	LocalDateTime fiveMinutesBefore = now.minusMinutes(5);
	List<EnvioDeMensagem> resultados = dao.getManager()
		.createNamedQuery("envioMensagem.ultimasDe5Minutos", EnvioDeMensagem.class)
		.setParameter("inicial", fiveMinutesBefore)
		.setParameter("final", now)
		.setParameter("agendado", StatusEntrega.AGENDADO)
		.setParameter("reenviar", StatusEntrega.REENVIAR)
		.getResultList();
		
	return resultados;
    }

    public void remover(EnvioDeMensagem mensagem) {
	dao.remover(mensagem);
    }

    public void atualizar(EnvioDeMensagem mensagem) {
	dao.atualizar(mensagem);
    }

    public PaginatedList paginado(int page, int max) {
	return dao.paginado(page, max);
    }

}
