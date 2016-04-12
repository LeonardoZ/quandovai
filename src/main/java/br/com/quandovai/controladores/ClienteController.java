package br.com.quandovai.controladores;

import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;
import br.com.quandovai.daos.ClienteDao;
import br.com.quandovai.modelo.PaginatedList;
import br.com.quandovai.modelo.entidade.Cliente;
import br.com.quandovai.modelo.entidade.Sexo;

@Controller
@Path(value = "/cliente")
public class ClienteController {

    @Inject
    private Validator validator;

    @Inject
    private ClienteDao clienteDao;

    @Inject
    private Result result;

    @Get("")
    public void listar(Integer page, String busca) {
	if (page == null) {
	    page = 0;
	}
	boolean ehBusca = busca != null && !busca.isEmpty();

	PaginatedList paginado = ehBusca ? clienteDao.paginado(busca, page, 10) : clienteDao.paginado(page, 10);
	result.include("clientesPaginados", paginado);
	if (ehBusca) {
	    result.include("termoDeBusca", busca);
	}
    }

    @Get("/cadastro")
    public void cadastro(Cliente cliente) {
	result.include("cliente", cliente);
	carregarDependenciasFormulario();
    }

    @Transactional
    @Post("/adicionar")
    public void adicionar(Cliente cliente) {
	validator.validate(cliente).onErrorRedirectTo(this).cadastro(cliente);
	clienteDao.salvar(cliente);
	result.redirectTo(this).listar(1, "");
    }

    @Get("/alterar/{cliente.id}")
    public void alterar(Cliente cliente) {
	cliente = clienteDao.buscaPorId(cliente.getId());
	result.include("cliente", cliente);
	carregarDependenciasFormulario();
    }

    @Transactional
    @Post("/atualizar/{id}")
    public void atualizar(Integer id, Cliente cliente) {
	cliente.setId(id);
	validator.validate(cliente).onErrorRedirectTo(this).alterar(cliente);
	clienteDao.atualizar(cliente);
	result.redirectTo(this).listar(1, "");
    }

    @Transactional
    @Post("/remover/{id}")
    public void remover(Long id) {
	if (id != null) {
	    Cliente cliente = clienteDao.buscaPorId(id);
	    clienteDao.remover(cliente);
	    result.redirectTo(this).listar(1, "");
	}
    }

    private void carregarDependenciasFormulario() {
	result.include("sexos", Sexo.values());
    }

}
