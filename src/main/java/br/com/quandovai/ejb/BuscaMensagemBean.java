package br.com.quandovai.ejb;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;

@Singleton
@Startup
@DependsOn(value = { "EnviaMensagemBean" })
public class BuscaMensagemBean {

    @Resource(lookup = "java:jboss/ee/concurrency/scheduler/pro")
    private ManagedScheduledExecutorService scheduledService;

    @EJB
    private EnviaMensagemBean enviaBean;

    @PostConstruct
    public void busca() {
	Runnable r = () -> enviaBean.enviarMensagens();
	scheduledService.scheduleAtFixedRate(r, 0, 5, TimeUnit.MINUTES);
    }

}
