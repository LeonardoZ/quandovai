package br.com.quandovai.modelo.servico;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.quandovai.modelo.EnvioDeMensagemFactory;
import br.com.quandovai.modelo.Periodo;
import br.com.quandovai.modelo.entidade.Cliente;
import br.com.quandovai.modelo.entidade.EnvioDeMensagem;
import br.com.quandovai.modelo.entidade.ModeloDeMensagem;
import br.com.quandovai.modelo.entidade.Sexo;
import br.com.quandovai.modelo.entidade.TipoEnvio;

public class EnvioDeMensagemFactoryTest {

    private Cliente cliente;
    private ModeloDeMensagem modelo;

    @Before
    public void setUp() throws Exception {
	modelo = new ModeloDeMensagem();
	modelo.setConteudo("Essa é um modelo de mensagem");
	modelo.setTipoDeEnvio(TipoEnvio.SMS);

	cliente = new Cliente();
	cliente.setNomeCompleto("Antonio Nunes");
	cliente.setEmail("antonio.nunes@gmail.com");
	cliente.setCelular("5514991233333");
	cliente.setSexo(Sexo.MASCULINO);
    }

    @Test
    public void agendarAgoraTest() {
	EnvioDeMensagemFactory agendador = new EnvioDeMensagemFactory(modelo, cliente);
	EnvioDeMensagem envio = agendador.enviarAgora();
	assertEquals(envio.getMensagem().getDateHoraDeEnvio().getHour(), LocalDateTime.now().getHour());
	assertEquals(envio.getMensagem().getDateHoraDeEnvio().getMinute(), LocalDateTime.now().getMinute());
    }

    @Test
    public void agendarEnvioSimplesTest() {
	EnvioDeMensagemFactory agendador = new EnvioDeMensagemFactory(modelo, cliente);
	LocalDateTime dataDeEnvio = LocalDateTime.of(2016, 4, 27, 10, 41);
	EnvioDeMensagem envio = agendador.agendarEnvioSimples(dataDeEnvio);

	assertEquals(envio.getMensagem().getDateHoraDeEnvio().getHour(), dataDeEnvio.getHour());
	assertEquals(envio.getMensagem().getDateHoraDeEnvio().getMinute(), dataDeEnvio.getMinute());
    }

    @Test
    public void agendarEnviosEspecificosTest() {
	EnvioDeMensagemFactory agendador = new EnvioDeMensagemFactory(modelo, cliente);
	LocalDateTime data1 = LocalDateTime.of(2016, 4, 27, 10, 41);
	LocalDateTime data2 = LocalDateTime.of(2016, 5, 17, 12, 41);
	LocalDateTime data3 = LocalDateTime.of(2016, 3, 12, 3, 41);
	LocalDateTime data4 = LocalDateTime.of(2016, 8, 20, 7, 41);
	List<EnvioDeMensagem> envios = agendador.agendarEnviosEspecificos(data1, data2, data3, data4);
	EnvioDeMensagem envio = envios.get(2);
	assertEquals(envio.getMensagem().getDateHoraDeEnvio().getHour(), data3.getHour());
	assertEquals(envio.getMensagem().getDateHoraDeEnvio().getMinute(), data3.getMinute());

	EnvioDeMensagem envio2 = envios.get(0);
	assertEquals(envio2.getMensagem().getDateHoraDeEnvio().getHour(), data1.getHour());
	assertEquals(envio2.getMensagem().getDateHoraDeEnvio().getMinute(), data1.getMinute());
    }

    @Test
    public void agendarEnviosComPeriodoTest() {
	EnvioDeMensagemFactory agendador = new EnvioDeMensagemFactory(modelo, cliente);
	LocalDateTime data1 = LocalDateTime.of(2016, 4, 27, 10, 41);
	LocalDateTime data2 = LocalDateTime.of(2016, 5, 27, 10, 41);
	LocalDateTime data3 = LocalDateTime.of(2016, 6, 27, 10, 41);
	LocalDateTime data4 = LocalDateTime.of(2016, 7, 27, 10, 41);

	List<EnvioDeMensagem> envios = agendador.agendarEnvioCom(Periodo.MENSAL, 4, data1);
	EnvioDeMensagem envio = envios.get(0);
	EnvioDeMensagem envio2 = envios.get(1);
	EnvioDeMensagem envio3 = envios.get(2);
	EnvioDeMensagem envio4 = envios.get(3);

	assertEquals(envio.getMensagem().getDateHoraDeEnvio().getMonth(), data1.getMonth());
	assertEquals(envio2.getMensagem().getDateHoraDeEnvio().getMonth(), data2.getMonth());
	assertEquals(envio3.getMensagem().getDateHoraDeEnvio().getMonth(), data3.getMonth());
	assertEquals(envio4.getMensagem().getDateHoraDeEnvio().getMonth(), data4.getMonth());
    }

}
