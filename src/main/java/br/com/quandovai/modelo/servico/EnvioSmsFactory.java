package br.com.quandovai.modelo.servico;

import br.com.quandovai.modelo.Provedor;

public class EnvioSmsFactory {

    public static EnvioSMS carregarServicoCom(Provedor provedor) {
	switch (provedor) {

	case SMS_API:
	    return new EnvioSMSApi();
	default:
	    break;
	}
	return null;
    }

}
