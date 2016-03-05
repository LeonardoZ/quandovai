package br.com.quandovai.agendado;

import br.com.quandovai.modelo.Provedor;
import br.com.quandovai.modelo.entidade.EnvioDeMensagem;
import br.com.quandovai.modelo.entidade.TipoEnvio;
import br.com.quandovai.modelo.servico.MeioDeEnvio;

public class EventoDeEnvio {

    private EnvioDeMensagem envio;
    private Provedor provedor;
    private MeioDeEnvio meioDeEnvio;

    public EventoDeEnvio(EnvioDeMensagem envio) {
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
