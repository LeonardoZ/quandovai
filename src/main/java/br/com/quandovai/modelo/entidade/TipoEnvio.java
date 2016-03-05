package br.com.quandovai.modelo.entidade;

import org.jboss.util.NotImplementedException;

import br.com.quandovai.modelo.Provedor;
import br.com.quandovai.modelo.servico.EnvioSmsFactory;
import br.com.quandovai.modelo.servico.MeioDeEnvio;

public enum TipoEnvio {
    
    SMS {
	@Override
	public MeioDeEnvio carregar(Provedor provedor) {
	    return EnvioSmsFactory.carregarServicoCom(provedor);
	}
    }, EMAIL {
	@Override
	public MeioDeEnvio carregar(Provedor provedor) {
	    throw new NotImplementedException();
	}
    };

    public static TipoEnvio porNome(String tipo) {
	return tipo == SMS.name() ? SMS : EMAIL;
    }
    
    public abstract MeioDeEnvio carregar(Provedor provedor) ;
    
}
