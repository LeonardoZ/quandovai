package br.com.quandovai.modelo.entidade;


import br.com.quandovai.modelo.Provedor;
import br.com.quandovai.modelo.servico.EnvioSmsFactory;
import br.com.quandovai.modelo.servico.MeioDeEnvio;

public enum TipoEnvio {

    SMS(EnvioSmsFactory::carregarServicoCom), EMAIL(null);

    private AcaoEnum acao;

    private TipoEnvio(AcaoEnum acao) {
	this.acao = acao;
    }

    public static TipoEnvio porNome(String tipo) {
	return tipo == SMS.name() ? SMS : EMAIL;
    }

    public interface AcaoEnum {
	MeioDeEnvio carregar(Provedor provedor);
    }
    
    public AcaoEnum getAcao() {
	return acao;
    }
}
