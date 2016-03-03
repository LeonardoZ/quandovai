package br.com.quandovai.modelo.servico;

import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.quartzjob.CronTask;
import br.com.quandovai.daos.EnvioDeMensagemDao;
import br.com.quandovai.modelo.entidade.EnvioDeMensagem;

@Controller
public class MensageiroBusca implements CronTask {
    
    private EnvioDeMensagemDao envioDao;
    private MensageiroEnvia mensageiroEnvia;
    
    @Inject
    public MensageiroBusca(EnvioDeMensagemDao envioDao) {
	this.envioDao = envioDao;
    }
    
    @Override
    public void execute() {
	List<EnvioDeMensagem> ultimasMensagens = envioDao.ultimasMensagens();
	mensageiroEnvia.prepararParaEnvio(ultimasMensagens);
    }

    @Override
    public String frequency() {
	return "0/5 * * * * ?";
    }

}
