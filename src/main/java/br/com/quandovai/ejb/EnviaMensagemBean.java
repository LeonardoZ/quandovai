package br.com.quandovai.ejb;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import br.com.quandovai.daos.EnvioDeMensagemDao;
import br.com.quandovai.modelo.entidade.EnvioDeMensagem;
import br.com.quandovai.modelo.entidade.TipoEnvio;
import br.com.quandovai.modelo.servico.MeioDeEnvio;

@Stateless(name = "EnviaMensagemBean")
public class EnviaMensagemBean {

    @Resource(lookup = "java:jboss/ee/concurrency/scheduler/pro")
    private ManagedScheduledExecutorService scheduledService;

    @Inject
    private EnvioDeMensagemDao envioDao;

    @Transactional(value = TxType.REQUIRED)
    public void enviarMensagens() {
	List<EnvioDeMensagem> todos = envioDao.ultimasMensagens();
	LinkedBlockingQueue<ScheduledFuture<EnvioDeMensagem>> fila = todos.stream()
		.map(this::configuraEnvioEspecifico)
		.collect(LinkedBlockingQueue::new, LinkedBlockingQueue::add, LinkedBlockingQueue::addAll);
	
	fila.forEach(f -> {
	    EnvioDeMensagem envioDeMensagem;
	    try {
		envioDeMensagem = f.get();
		envioDao.atualizar(envioDeMensagem);
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	});

    }

    private ScheduledFuture<EnvioDeMensagem> configuraEnvioEspecifico(EnvioDeMensagem e) {
	long ate = LocalDateTime.now().until(e.getMensagem().horaDeEnvioAjustada(), ChronoUnit.MINUTES);
	Callable<EnvioDeMensagem> acaoDeEnvio = () -> enviar(e);
	return scheduledService.schedule(acaoDeEnvio, ate, TimeUnit.MINUTES);
    }

    private EnvioDeMensagem enviar(EnvioDeMensagem e) {
	TipoEnvio tipoDeEnvio = e.getMensagem().getTipoDeEnvio();
	MeioDeEnvio meioDeEnvio = tipoDeEnvio.getAcao().carregar(e.getProvedor());
	meioDeEnvio.enviar(e);
	e.confirmarEnvio();
	return e;

    }

}
