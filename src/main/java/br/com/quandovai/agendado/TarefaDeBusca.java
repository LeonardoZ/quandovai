package br.com.quandovai.agendado;

import java.util.List;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import br.com.caelum.vraptor.tasks.Task;
import br.com.caelum.vraptor.tasks.scheduler.Scheduled;
import br.com.quandovai.daos.EnvioDeMensagemDao;
import br.com.quandovai.modelo.entidade.EnvioDeMensagem;

@Scheduled(fixedRate = 5000, concurrent = false, id = "tarefaBusca120")
public class TarefaDeBusca implements Task {

    /**
     * Veja <code>br.com.agoravai.agendado.TarefaDeEnvio</code>
     */
    @Any
    @Inject
    private Event<EnvioDeMensagem> eventoDeEnvio;

    @Inject
    private EnvioDeMensagemDao envioDao;

    @Override
    public void execute() {
	List<EnvioDeMensagem> envios = envioDao.ultimasMensagens();
	envios.forEach(eventoDeEnvio::fire);
    }

}
