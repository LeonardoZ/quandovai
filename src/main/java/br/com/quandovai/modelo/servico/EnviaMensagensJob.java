package br.com.quandovai.modelo.servico;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.quandovai.modelo.entidade.Mensagem;

public class EnviaMensagensJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
	System.out.println("EnviaMensagensJob.execute()");
	Mensagem mensagem = (Mensagem) context.getJobDetail().getJobDataMap().get("MENSAGEM");
	System.out.println(mensagem.getConteudo());
    }

}
