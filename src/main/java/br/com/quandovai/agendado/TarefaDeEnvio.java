package br.com.quandovai.agendado;

import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;

import br.com.quandovai.modelo.Provedor;
import br.com.quandovai.modelo.entidade.EnvioDeMensagem;
import br.com.quandovai.modelo.entidade.TipoEnvio;
import br.com.quandovai.modelo.servico.MeioDeEnvio;

public class TarefaDeEnvio {
    
    private EnvioDeMensagem envio;
    private Provedor provedor;
    private MeioDeEnvio meioDeEnvio;

    public void aoReceberTarefa(@Observes(during = TransactionPhase.AFTER_COMPLETION) EnvioDeMensagem envio) {
	this.envio = envio;
	this.provedor = envio.getProvedor();
	carregarMeioDeEnvio();
    }

    private void carregarMeioDeEnvio() {
	TipoEnvio tipoDeEnvio = envio.getMensagem().getTipoDeEnvio();
	meioDeEnvio = tipoDeEnvio.carregar(provedor);
	meioDeEnvio.enviar(envio);
    }

    
}
