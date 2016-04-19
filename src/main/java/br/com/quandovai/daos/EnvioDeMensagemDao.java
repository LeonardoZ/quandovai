package br.com.quandovai.daos;

import java.time.LocalDateTime;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

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
		System.out.println(dao.getManager());
		return dao.todos();
	}

	public void salvar(EnvioDeMensagem envio) {
		dao.salvar(envio);
	}

	public void salvar(List<EnvioDeMensagem> mensagens) {
		dao.salvar(mensagens, 100);
	}

	public EnvioDeMensagem buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public List<EnvioDeMensagem> ultimasMensagens() {
		// inicial - final
		// status agendado e reenviado
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime fiveMinutesAfter = now.plusMinutes(5);

		EntityManager manager = dao.getManager();
		List<EnvioDeMensagem> resultados = manager
				.createNamedQuery("envioMensagem.ultimasDe5Minutos", EnvioDeMensagem.class).setParameter("inicial", now)
				.setParameter("final", fiveMinutesAfter).setParameter("agendado", StatusEntrega.AGENDADO)
				.setParameter("reenviar", StatusEntrega.REENVIAR).getResultList();
		System.out.println(resultados);
		return resultados;
	}

	public void remover(EnvioDeMensagem envio) {
		dao.remover(envio);
	}

	public void atualizar(EnvioDeMensagem envio) {
		dao.atualizar(envio);

	}

	public PaginatedList paginado(String busca, int page, int max) {
		TypedQuery<EnvioDeMensagem> resultados = dao.getManager().createNamedQuery("envioMensagem.buscaPorConteudo",
				EnvioDeMensagem.class);
		busca = "%" + busca + "%";
		resultados.setParameter("conteudo", busca);
		TypedQuery<Number> contagem = dao.getManager().createNamedQuery("envioMensagem.countPorConteudo", Number.class);
		contagem.setParameter("conteudo", busca);
		return dao.paginado(resultados, contagem, page, max);
	}

	public PaginatedList paginado(int page, int max) {
		return dao.paginado(page, max);
	}

}
