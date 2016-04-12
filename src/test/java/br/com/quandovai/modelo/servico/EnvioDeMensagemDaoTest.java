package br.com.quandovai.modelo.servico;

import java.io.File;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.CreateSchema;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.quandovai.daos.ClienteDao;
import br.com.quandovai.daos.EnvioDeMensagemDao;
import br.com.quandovai.daos.ModeloDeMensagemDao;
import br.com.quandovai.modelo.EnvioDeMensagemFactory;
import br.com.quandovai.modelo.Periodo;
import br.com.quandovai.modelo.Provedor;
import br.com.quandovai.modelo.StatusEntrega;
import br.com.quandovai.modelo.entidade.Cliente;
import br.com.quandovai.modelo.entidade.EnvioDeMensagem;
import br.com.quandovai.modelo.entidade.ModeloDeMensagem;

@RunWith(Arquillian.class)
@CreateSchema(value = "scripts/populate.sql")
public class EnvioDeMensagemDaoTest {

    @Inject
    private EnvioDeMensagemDao dao;

    @Inject
    private ClienteDao clienteDao;

    @Inject
    private ModeloDeMensagemDao modeloDao;

    private EnvioDeMensagemFactory factory;

    @Deployment
    public static Archive<?> criarArquivoTeste() {
	File[] dependencias = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeDependencies().resolve()
		.withTransitivity().asFile();
	Archive<?> arquivoTeste = ShrinkWrap.create(WebArchive.class, "QuandoVaiTeste.war")
		.addPackage(EnvioDeMensagemDao.class.getPackage()).addPackage(EnvioDeMensagem.class.getPackage())
		.addPackages(true, "br.com.quandovai", "br.com.quandovai.servico").addAsLibraries(dependencias)
		.addAsWebInfResource("test-ds.xml")
		// Adicionando o arquivo persistence.xml para conexão
		// JPAR
		.addAsResource("META-INF/test-persistence.xml", ArchivePaths.create("META-INF/persistence.xml"))
		// Adicionando o beans.xml para ativação do CDI
		.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	return arquivoTeste;
    }

    @Before
    public void setUp() {
	configuraTeste();
    }

    private void configuraTeste() {
	ModeloDeMensagem modelo = modeloDao.buscaPorId(1l);
	Cliente cliente = clienteDao.buscaPorId(1l);
	factory = new EnvioDeMensagemFactory(modelo, cliente, Provedor.SMS_API);
    }

    @Test
    public void testeInserirEnvio() {
	configuraTeste();
	EnvioDeMensagem envioDeMensagem = factory.enviarAgora();
	dao.salvar(envioDeMensagem);
	assertEnvioSalvo(envioDeMensagem);
    }

    @Test
    public void testeInserirVarios() {
	configuraTeste();
	List<EnvioDeMensagem> envios = factory.agendarEnvioCom(Periodo.MENSAL, 12, LocalDateTime.now());
	dao.salvar(envios);
	envios.forEach(this::assertEnvioSalvo);
    }

    @Test
    public void testeBuscaUltimos() {
	configuraTeste();
	LocalDateTime base = LocalDateTime.now();
	LocalDateTime d1 = base.plus(1, ChronoUnit.MINUTES);
	LocalDateTime d2 = base.plus(2, ChronoUnit.MINUTES);
	List<EnvioDeMensagem> envios = factory.agendarEnviosEspecificos(base, d1, d2);
	dao.salvar(envios);

	List<EnvioDeMensagem> ultimasMensagens = dao.ultimasMensagens();
	Assert.assertTrue(ultimasMensagens.size() == envios.size());
	Assert.assertTrue(ultimasMensagens.get(0).getMensagem().getDateHoraDeEnvio().equals(d2));
    }
    

    @Test
    public void testeBuscaUltimosDisponiveis() {
	configuraTeste();
	LocalDateTime base = LocalDateTime.now();
	LocalDateTime d1 = base.plus(1, ChronoUnit.MINUTES);
	LocalDateTime d2 = base.plus(2, ChronoUnit.MINUTES);
	LocalDateTime d3 = base.plus(7, ChronoUnit.MINUTES);
	List<EnvioDeMensagem> envios = factory.agendarEnviosEspecificos(base, d1, d2);
	EnvioDeMensagem envioAposLimite = factory.agendarEnvioSimples(d3);
	dao.salvar(envios);
	dao.salvar(envioAposLimite);

	List<EnvioDeMensagem> ultimasMensagens = dao.ultimasMensagens();

	Assert.assertTrue(ultimasMensagens.size() == envios.size());
	Assert.assertTrue(ultimasMensagens.get(0).getMensagem().getDateHoraDeEnvio().equals(d2));
	Assert.assertTrue(!ultimasMensagens.contains(envioAposLimite));
    }

    @Test
    public void testeAtualizar() {
	EnvioDeMensagem envioDeMensagem = factory.enviarAgora();
	envioDeMensagem = dao.buscaPorId(1l);
	envioDeMensagem.confirmarEnvio();
	dao.atualizar(envioDeMensagem);
	envioDeMensagem = dao.buscaPorId(1l);

	Assert.assertTrue(envioDeMensagem.getEnviadoEm() != null);
	Assert.assertTrue(envioDeMensagem.getStatus().equals(StatusEntrega.ENVIADO));
    }

    private void assertEnvioSalvo(EnvioDeMensagem envioDeMensagem) {
	Assert.assertTrue(envioDeMensagem.getId() > 0);
	Assert.assertTrue(envioDeMensagem.getMensagem().getId() > 0);
	Assert.assertTrue(envioDeMensagem.getStatus().equals(StatusEntrega.AGENDADO));
    }

}
