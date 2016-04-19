package br.com.quandovai.modelo.servico;

import br.com.quandovai.modelo.entidade.EnvioDeMensagem;

public interface MeioDeEnvio {

	void enviar(EnvioDeMensagem envio);

}
