package br.com.quandovai.controladores;

import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;
import br.com.quandovai.daos.ModeloDeMensagemDao;
import br.com.quandovai.modelo.PaginatedList;
import br.com.quandovai.modelo.entidade.ModeloDeMensagem;
import br.com.quandovai.modelo.entidade.TipoEnvio;

@Controller
@Path(value = "/modelo")
public class ModeloController {

    @Inject
    private Validator validator;

    @Inject
    private ModeloDeMensagemDao modeloDao;

    @Inject
    private Result result;

    @Get("")
    public void listar(Integer page, String busca) {
	if (page == null) {
	    page = 0;
	}
	boolean ehBusca = busca != null && !busca.isEmpty();

	PaginatedList paginado = ehBusca ? modeloDao.paginado(busca, page, 10) : modeloDao.paginado(page, 10);
	result.include("modelosPaginados", paginado);
	if (ehBusca) {
	    result.include("termoDeBusca", busca);
	}
    }

    @Get("/cadastro")
    public void cadastro(ModeloDeMensagem modelo) {
	result.include("modelo", modelo);
	carregarDependenciasFormulario();
    }

    @Transactional
    @Post("/adicionar")
    public void adicionar(ModeloDeMensagem modelo) {
	validator.validate(modelo).onErrorRedirectTo(this).cadastro(modelo);
	modeloDao.salvar(modelo);
	result.redirectTo(this).listar(0, "");
    }

    @Get("/alterar/{modelo.id}")
    public void alterar(ModeloDeMensagem modelo) {
	modelo = modeloDao.buscaPorId(modelo.getId());
	result.include("modelo", modelo);
	carregarDependenciasFormulario();
    }

    @Transactional
    @Post("/atualizar/{id}")
    public void atualizar(Integer id, ModeloDeMensagem modelo) {
	modelo.setId(id);
	validator.validate(modelo).onErrorRedirectTo(this).alterar(modelo);
	modeloDao.atualizar(modelo);
	result.redirectTo(this).listar(0, "");
    }

    @Transactional
    @Post("/remover/{id}")
    public void remover(Long id) {
	if (id != null) {
	    ModeloDeMensagem modelo = modeloDao.buscaPorId(id);
	    modeloDao.remover(modelo);
	    result.redirectTo(this).listar(0, "");
	}
    }

    private void carregarDependenciasFormulario() {
	result.include("tiposDeEnvio", TipoEnvio.values());
    }

}
