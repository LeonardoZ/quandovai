package br.com.quandovai.modelo;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.quandovai.modelo.entidade.Cliente;
import br.com.quandovai.modelo.entidade.EnvioDeMensagem;
import br.com.quandovai.modelo.entidade.Mensagem;
import br.com.quandovai.modelo.entidade.ModeloDeMensagem;
import br.com.quandovai.modelo.entidade.TipoEnvio;

public class EnvioDeMensagemFactory {

    private ModeloDeMensagem modelo;
    private Cliente cliente;
    
    public EnvioDeMensagemFactory(ModeloDeMensagem modelo, Cliente cliente) {
	this.modelo = modelo;
	this.cliente = cliente;
    }

    /**
     * Calcular datas de envio Gerar mensagem para cada data Criar o Envio
     * Agrupar envios em lista
     * 
     * @return Mensagens para enviar
     */
    public List<EnvioDeMensagem> agendarEnvioCom(Periodo periodoDeEnvio, int quantasVezes, LocalDateTime aPartirDe) {
	if (quantasVezes < 1) {
	    throw new IllegalArgumentException("O número mínimo de mensagens é 1");
	}
	deveEstarNoFuturo(aPartirDe);

	LocalDateTime ultimaData = aPartirDe;
	List<LocalDateTime> dias = new LinkedList<>();
	for (int i = 0; i < quantasVezes; i++) {
	    dias.add(ultimaData);
	    LocalDateTime adiantada = periodoDeEnvio.getAjuste().adiantar(ultimaData);
	    ultimaData = adiantada;
	}
	List<EnvioDeMensagem> envios = 
		dias.stream()
			.map(this::agendarEnvioSimples)
			.collect(Collectors.toList());
	return envios;
    }

    public List<EnvioDeMensagem> agendarEnviosEspecificos(LocalDateTime... datasEspecifica) {
	List<EnvioDeMensagem> envios = Arrays.asList(datasEspecifica).stream().map(this::agendarEnvioSimples)
		.collect(Collectors.toList());
	return envios;
    }

    public EnvioDeMensagem enviarAgora() {
	return criaMensagem(LocalDateTime.now());
    }

    public EnvioDeMensagem agendarEnvioSimples(LocalDateTime dataEspecifica) {
	deveEstarNoFuturo(dataEspecifica);
	return criaMensagem(dataEspecifica);
    }

    private EnvioDeMensagem criaMensagem(LocalDateTime dataEspecifica) {
	Mensagem mensagem = new Mensagem();
	mensagem.setConteudo(modelo.getConteudo());
	mensagem.setDateHoraDeEnvio(dataEspecifica);
	mensagem.setTipoDeEnvio(TipoEnvio.SMS);
	return EnvioDeMensagem.criarEnvio(cliente, mensagem);
    }

    public void deveEstarNoFuturo(LocalDateTime data) {
	if (!data.isAfter(LocalDateTime.now())) {
	    throw new IllegalArgumentException();
	}
    }
}
