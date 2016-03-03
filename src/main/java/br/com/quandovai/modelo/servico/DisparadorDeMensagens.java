package br.com.quandovai.modelo.servico;

import java.util.List;

import javax.inject.Inject;

import br.com.quandovai.daos.EnvioDeMensagemDao;
import br.com.quandovai.modelo.entidade.EnvioDeMensagem;

public class DisparadorDeMensagens {

    private EnvioDeMensagemDao envioDao;

    @Inject
    public DisparadorDeMensagens(EnvioDeMensagemDao envioDao) {
	this.envioDao = envioDao;
    }

    public void disparar(List<EnvioDeMensagem> envios) {
	envioDao.salvar(envios);
    }

    public void confirmarMensagemRecebida(EnvioDeMensagem envio) {
	envio.confirmarEnvio();
	envioDao.atualizar(envio);
    }


}
