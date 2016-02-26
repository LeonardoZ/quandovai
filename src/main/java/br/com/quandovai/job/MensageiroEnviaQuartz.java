package br.com.quandovai.job;

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

import br.com.quandovai.modelo.MensageiroEnvia;
import br.com.quandovai.modelo.Mensagem;

public class MensageiroEnviaQuartz implements MensageiroEnvia {

    private SchedulerFactory factory;
    private Scheduler scheduler;
    private JobDetail jobDetail;

    @Override
    public void prepararParaEnvio(List<Mensagem> mensagens) {
	configuraQuartz();
	System.out.println("MensageiroEnviaQuartz.prepararParaEnvio()");
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

    private void criaTarefaDeEnvio(Mensagem mensagem) {

	try {
	    JobDataMap params = new JobDataMap();
	    params.put("MENSAGEM", mensagem);
	    
	    jobDetail = 
		    JobBuilder
		    .newJob(EnviaMensagensJob.class)
		    .withIdentity("enviaMensagem"+mensagem.getId(), "mensagens")
		    .build();
	    
	    Date dataEnvio = mensagem.ehAntigaNaoEnviada() ? mensagem.novaDataDeEnvio() : mensagem.dateHoraDeEnvioConvertido();
	    System.out.println("Nova data "+dataEnvio);
	    SimpleTrigger trigger = (SimpleTrigger)
		    TriggerBuilder.newTrigger()
                	    .withIdentity("triggerEnvia"+mensagem.getId(), "mensagens")
                	    .startAt(dataEnvio) 
                	    .build();
	    scheduler.scheduleJob(jobDetail, trigger);
	} catch (SchedulerException e) {
	    e.printStackTrace();
	}
    }

}
