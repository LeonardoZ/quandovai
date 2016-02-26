package br.com.quandovai.job;

import java.util.List;

import javax.inject.Inject;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.quandovai.daos.MensagemDao;
import br.com.quandovai.modelo.MensageiroEnvia;
import br.com.quandovai.modelo.Mensagem;

public class BuscaMensagensJob implements Job {
    
    @Inject
    private MensagemDao mensagemDao;
    private MensageiroEnvia mensageiroEnvia = new MensageiroEnviaQuartz();
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
	System.out.println("BuscaMensagensJob.execute()");
	List<Mensagem> mensagens = mensagemDao.todos();
	System.out.println(mensagens);
	mensageiroEnvia.prepararParaEnvio(mensagens);
    }

}
