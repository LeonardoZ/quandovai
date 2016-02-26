package br.com.quandovai.job;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import br.com.quandovai.modelo.MensageiroBusca;

public class MensageiroBuscaQuartz implements MensageiroBusca {

    @Override
    public void buscarMensagens() {
	try {
	    SchedulerFactory schedFact = new StdSchedulerFactory();
	    Scheduler sched = schedFact.getScheduler();
	    sched.start();

	    JobDetail jobDetail = JobBuilder.newJob(BuscaMensagensJob.class).withIdentity("buscaMensagens", "mensagens")
		    .build();

	    CronTrigger trigger = TriggerBuilder.newTrigger()
		    .withIdentity("de5Em5Minutos", "mensagens")
		    .withSchedule(CronScheduleBuilder
	            .cronSchedule("0/10 * * * * ?"))
		    .build();

	    sched.scheduleJob(jobDetail, trigger);
	} catch (SchedulerException e) {
	    e.printStackTrace();
	}
    }

}
