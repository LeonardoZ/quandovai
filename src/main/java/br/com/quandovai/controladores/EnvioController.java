package br.com.quandovai.controladores;

import static br.com.caelum.vraptor.view.Results.json;

import java.time.LocalDateTime;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;
import br.com.quandovai.daos.ClienteDao;
import br.com.quandovai.daos.EnvioDeMensagemDao;
import br.com.quandovai.daos.ModeloDeMensagemDao;
import br.com.quandovai.modelo.FabricaDeEnvio;
import br.com.quandovai.modelo.FabricaDeEnvio.FabricaMultipla;
import br.com.quandovai.modelo.FabricaDeEnvio.FabricaSimples;
import br.com.quandovai.modelo.PaginatedList;
import br.com.quandovai.modelo.Periodo;
import br.com.quandovai.modelo.PreparoEnvio;
import br.com.quandovai.modelo.Provedor;
import br.com.quandovai.modelo.entidade.Cliente;
import br.com.quandovai.modelo.entidade.EnvioDeMensagem;

@Controller
@Path(value = "/envio")
public class EnvioController {

    @Inject
    private Validator validator;

    @Inject
    private EnvioDeMensagemDao envioDao;

    @Inject
    private ModeloDeMensagemDao modeloDao;

    @Inject
    private ClienteDao clienteDao;

    @Inject
    private Result result;

    @Get("/cockpit")
    public void cockpit() {

    }

    @Get("")
    public void listar(Integer page, String busca) {
	if (page == null) {
	    page = 0;
	}
	boolean ehBusca = busca != null && !busca.isEmpty();

	PaginatedList paginado = ehBusca ? envioDao.paginado(busca, page, 10) : envioDao.paginado(page, 10);
	result.include("mensagensPaginadas", paginado);
	if (ehBusca) {
	    result.include("conteudo", busca);
	}
    }

    @Get("/cadastro")
    public void cadastro(PreparoEnvio preparo) {
	result.include("preparo", preparo);
	carregarDependenciasFormulario();
    }

    @Transactional
    @Post("/adicionar")
    public void adicionar(PreparoEnvio preparo) {
	validator.validate(preparo).onErrorRedirectTo(this).cadastro(preparo);
	// TODO
	// envioDao.salvar(preparo);
	result.redirectTo(this).listar(1, "");
    }

    @Get("/alterar/{envio.id}")
    public void alterar(EnvioDeMensagem envio) {
	envio = envioDao.buscaPorId(envio.getId());
	result.include("envio", envio);
	carregarDependenciasFormulario();
    }

    @Transactional
    @Post("/atualizar/{id}")
    public void atualizar(Integer id, EnvioDeMensagem envio) {
	envio.setId(id);
	validator.validate(envio).onErrorRedirectTo(this).alterar(envio);
	envioDao.atualizar(envio);
	result.redirectTo(this).listar(1, "");
    }

    @Transactional
    @Post("/remover/{id}")
    public void remover(Long id) {
	if (id != null) {
	    EnvioDeMensagem envio = envioDao.buscaPorId(id);
	    envioDao.remover(envio);
	    result.redirectTo(this).listar(1, "");
	}
    }

    @Get("/busca/cliente/{nomeDoCliente}")
    public void buscaClientesAjax(String nomeDoCliente) {
	validator.onErrorSendBadRequest();
	List<Cliente> encontrados = clienteDao.buscaPorNome(nomeDoCliente);
	result.use(json()).from(encontrados, "clientes").exclude("usuario").serialize();
    }

    @Post("/calcular")
    public void calcularEnviosAjax(PreparoEnvio preparo) {
	validator.onErrorSendBadRequest();

	List<Long> ids = preparo.getIdsClientes();
	List<Cliente> clientes = clienteDao.buscaPorIds(ids);
	preparo.setDestinatarios(clientes);
	
	Provedor provedor = preparo.getProvedor();
	LocalDateTime dataHoraBase = preparo.getDataHoraBase();
	String conteudo = preparo.getConteudo();
	
	// Fabrica é um Builder
	FabricaDeEnvio fabrica;
	fabrica = FabricaDeEnvio.construirSemModelo(conteudo, provedor, dataHoraBase);

	if (preparo.ehUnicoEnvio()) {
	    FabricaSimples fabricaSimples = fabrica.unicaMensagem();
	    EnvioDeMensagem envio = fabricaSimples.unicoCliente(preparo.primeiroCliente()).construir();
	    result.use(json()).from(envio).recursive().serialize();
	} else {
	    FabricaMultipla fabricaMultipla = fabrica.variasMensagens();
	    if (!preparo.ehUnicaData()) {
		fabricaMultipla.variasMensagens(preparo.getQuantidade(), preparo.getPeriodo());
	    }
	    if (preparo.ehUnicoCliente()) {
		fabricaMultipla.unicoCliente(preparo.primeiroCliente());
	    } else {
		fabricaMultipla.variosClientes(preparo.getDestinatarios());
	    }
	    List<EnvioDeMensagem> envios = fabricaMultipla.construir();
	    result.use(json()).from(envios).recursive().serialize();
	}

	// List<Cliente> encontrados = clienteDao.buscaPorNome(nomeDoCliente);
	// result.use(json()).from(encontrados,
	// "clientes").exclude("usuario").serialize();
    }

    private void carregarDependenciasFormulario() {
	result.include("modelos", modeloDao.todos());
	result.include("periodos", Periodo.values());
	result.include("provedores", Provedor.values());
    }

    @Get("")
    public void listarClientes(Integer page, String busca) {
	if (page == null) {
	    page = 0;
	}
	boolean ehBusca = busca != null && !busca.isEmpty();

	PaginatedList paginado = ehBusca ? clienteDao.paginado(busca, page, 10) : clienteDao.paginado(page, 10);
	result.use(json()).from(paginado, "clientesPaginados").recursive().serialize();
	if (ehBusca) {
	    result.include("termoDeBuscaCliente", busca);
	}
    }
}
