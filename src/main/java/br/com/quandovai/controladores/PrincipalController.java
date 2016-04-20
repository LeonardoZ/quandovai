package br.com.quandovai.controladores;

import static br.com.caelum.vraptor.view.Results.json;
import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Result;
import br.com.quandovai.daos.EnvioDeMensagemDao;
import br.com.quandovai.modelo.PaginatedList;

@Controller
public class PrincipalController {
	
	@Inject
	private EnvioDeMensagemDao envioDao;
	
	@Inject
	private Result result;

	@Get("/")
	public void index() {
		
	}
	
	@Get("/mensagens/cadastradas")
	public void carregaMensagensCadastradasAjax(int page) {

		PaginatedList paginado = envioDao.paginadoCadastradas(page, 10);
		result.use(json()).from(paginado).recursive().serialize();
	
	}

}
