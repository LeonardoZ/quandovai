package br.com.quandovai.modelo.entidade;

public enum TipoEnvio {
    
    SMS, EMAIL;

    public static TipoEnvio porNome(String tipo) {
	return tipo == SMS.name() ? SMS : EMAIL;
    }
    
}
