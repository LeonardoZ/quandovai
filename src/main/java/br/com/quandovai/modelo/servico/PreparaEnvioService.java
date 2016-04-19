package br.com.quandovai.modelo.servico;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

import javax.inject.Inject;

import br.com.quandovai.daos.ClienteDao;
import br.com.quandovai.modelo.FabricaDeEnvio;
import br.com.quandovai.modelo.FabricaDeEnvio.FabricaMultipla;
import br.com.quandovai.modelo.FabricaDeEnvio.FabricaSimples;
import br.com.quandovai.modelo.PreparoEnvio;
import br.com.quandovai.modelo.Provedor;
import br.com.quandovai.modelo.entidade.Cliente;
import br.com.quandovai.modelo.entidade.EnvioDeMensagem;

public class PreparaEnvioService {

    
    private ClienteDao clienteDao;
    private PreparoEnvio preparo;
    private FabricaDeEnvio fabrica;
    
    @Inject
    public PreparaEnvioService(ClienteDao clienteDao) {
	this.clienteDao = clienteDao;
    }
    
    public void preparar(
	    PreparoEnvio preparo, 
	    Consumer<EnvioDeMensagem> callbackSimples,
	    Consumer<List<EnvioDeMensagem>> callbackMultiplo) {
	this.preparo = preparo;
	this.fabrica = configurar(preparo);

	if (preparo.ehUnicoEnvio()) {
	    executaPreparoUnico(callbackSimples);
	} else {
	    executaPreparoMultiplo(callbackMultiplo);
	}
    }

    private void executaPreparoMultiplo(Consumer<List<EnvioDeMensagem>> callbackMultiplo) {
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
	callbackMultiplo.accept(envios);
    }

    private void executaPreparoUnico(Consumer<EnvioDeMensagem> callbackSimples) {
	FabricaSimples fabricaSimples = fabrica.unicaMensagem();
	EnvioDeMensagem envio = fabricaSimples
                        		.unicoCliente(preparo.primeiroCliente())
                        		.construir();
	callbackSimples.accept(envio);
    }

    private FabricaDeEnvio configurar(PreparoEnvio preparo) {
	List<Long> ids = preparo.getIdsClientes();
	List<Cliente> clientes = clienteDao.buscaPorIds(ids);
	preparo.setDestinatarios(clientes);

	Provedor provedor = preparo.getProvedor();
	LocalDateTime dataHoraBase = preparo.getDataHoraBase();
	String conteudo = preparo.getConteudo();

	// Fabrica é um Builder
	return FabricaDeEnvio.construir(conteudo, provedor, dataHoraBase);
    }

}
