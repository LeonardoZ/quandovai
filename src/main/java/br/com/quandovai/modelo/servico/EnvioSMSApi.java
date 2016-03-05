package br.com.quandovai.modelo.servico;

import org.jboss.util.NotImplementedException;

import br.com.quandovai.modelo.entidade.EnvioDeMensagem;

/**
 * Vendor SMS API
 **/
public class EnvioSMSApi implements EnvioSMS {

    @Override
    public void enviar(EnvioDeMensagem envio) {
	throw new NotImplementedException();
    }

}
