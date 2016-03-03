package br.com.quandovai.modelo.servico;

import java.util.Date;
import java.util.List;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import br.com.quandovai.modelo.entidade.EnvioDeMensagem;

public class MensageiroEnvia {

    private SchedulerFactory factory;
    private Scheduler scheduler;
    private JobDetail jobDetail;

    public void prepararParaEnvio(List<EnvioDeMensagem> mensagens) {
	configuraQuartz();
	mensagens.forEach(this::criaTarefaDeEnvio);
    }

    void configuraQuartz() {
	try {
	    factory = new StdSchedulerFactory();
	    scheduler = factory.getScheduler();
	    scheduler.start();

	} catch (SchedulerException e) {
	    e.printStackTrace();
	}
    }

    private void criaTarefaDeEnvio(EnvioDeMensagem envio) {

	try {
	    JobDataMap params = new JobDataMap();
	    params.put("MENSAGEM", envio);

	    jobDetail = JobBuilder.newJob(EnviaMensagensJob.class).usingJobData(params)
		    .withIdentity("enviaMensagem" + envio.getId(), "mensagens").build();

	    Date dataEnvio = envio.getMensagem().ehAntigaNaoEnviada() 
		    ? envio.getMensagem().novaDataDeEnvio()
		    : envio.getMensagem().dateHoraDeEnvioConvertido();
		    
	    SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger()
		    .withIdentity("triggerEnvia" + envio.getId(), "mensagens").startAt(dataEnvio).build();
	 
	    scheduler.scheduleJob(jobDetail, trigger);
	
	} catch (SchedulerException e) {
	    e.printStackTrace();
	}
    }
}
