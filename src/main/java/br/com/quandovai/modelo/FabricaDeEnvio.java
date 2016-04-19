package br.com.quandovai.modelo;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import br.com.quandovai.modelo.entidade.Cliente;
import br.com.quandovai.modelo.entidade.EnvioDeMensagem;
import br.com.quandovai.modelo.entidade.Mensagem;
import br.com.quandovai.modelo.entidade.TipoEnvio;

public class FabricaDeEnvio {

	private Provedor provedor;
	private String conteudo;
	private LocalDateTime dataHoraBase;

	public static FabricaDeEnvio construir(String conteudo, Provedor provedor, LocalDateTime dataHoraBase) {
		return new FabricaDeEnvio(conteudo, provedor, dataHoraBase);
	}

	private FabricaDeEnvio(String conteudo, Provedor provedor, LocalDateTime dataHoraBase) {
		this.conteudo = conteudo;
		this.provedor = provedor;
		this.dataHoraBase = dataHoraBase;
	}

	public FabricaMultipla variasMensagens() {
		return new FabricaMultipla();
	}

	public FabricaSimples unicaMensagem() {
		return new FabricaSimples();
	}

	public class FabricaMultipla {

		private List<Cliente> clientes;
		private int quantidade;
		private Periodo periodo;
		private Cliente cliente;

		private FabricaMultipla() {
		}

		public FabricaMultipla variosClientes(List<Cliente> clientes) {
			this.clientes = clientes;
			return this;
		}

		public FabricaMultipla variasMensagens(int quantidade, Periodo periodo) {
			this.quantidade = quantidade;
			this.periodo = periodo;
			return this;
		}

		public FabricaMultipla unicoCliente(Cliente cliente) {
			this.cliente = cliente;
			return this;
		}

		public List<EnvioDeMensagem> construir() {
			boolean temVariasDatas = periodo != null || quantidade > 0;
			boolean temClientes = clientes != null;
			boolean temCliente = cliente != null;

			// P/ cada cliente mande várias mensagens
			if (temClientes && temVariasDatas) {
				return enviaComVariosClientesEDatas();
			}

			// P/ esse cliente mande várias mensagens
			else if (temCliente && temVariasDatas) {
				return enviaComClienteEVariasDatas();
			}

			// Nessa data mande várias mensagens para vários clientes
			else if (temClientes && !temVariasDatas) {
				return enviaComClientesEmUmaData();
			}

			throw new IllegalArgumentException("Situação de parâmetros incorreta");
		}

		private List<EnvioDeMensagem> enviaComVariosClientesEDatas() {
			final List<LocalDateTime> datas = gerarDatas();

			BiFunction<Cliente, List<LocalDateTime>, List<EnvioDeMensagem>> t = (c, ds) -> ds.stream()
					.map(d -> criaMensagem(d, c)).collect(Collectors.toList());

			Function<Cliente, List<EnvioDeMensagem>> f = (cli) -> t.apply(cli, datas);

			List<EnvioDeMensagem> envios = clientes.stream().map(f).flatMap(l -> l.stream())
					.collect(Collectors.toList());

			return envios;
		}

		private List<EnvioDeMensagem> enviaComClienteEVariasDatas() {
			List<EnvioDeMensagem> envios = gerarDatas().stream().map(d -> criaMensagem(d, cliente))
					.collect(Collectors.toList());
			return envios;
		}

		private List<EnvioDeMensagem> enviaComClientesEmUmaData() {
			List<EnvioDeMensagem> envios = clientes.stream().map(c -> criaMensagem(dataHoraBase, c))
					.collect(Collectors.toList());
			return envios;
		}

		private List<LocalDateTime> gerarDatas() {
			if (quantidade < 1) {
				throw new IllegalArgumentException("O número mínimo de mensagens é 1");
			}
			if (!dataHoraBase.isAfter(LocalDateTime.now())) {
				throw new IllegalArgumentException();
			}

			LocalDateTime ultimaData = dataHoraBase;
			List<LocalDateTime> datas = new LinkedList<>();
			for (int i = 0; i < quantidade; i++) {
				datas.add(ultimaData);
				LocalDateTime adiantada = periodo.getAjuste().adiantar(ultimaData);
				ultimaData = adiantada;
			}
			return datas;
		}

	}

	public class FabricaSimples {

		private Cliente cliente;

		private FabricaSimples() {

		}

		public FabricaSimples unicoCliente(Cliente cliente) {
			this.cliente = cliente;
			return this;
		}

		public EnvioDeMensagem construir() {
			return criaMensagem(dataHoraBase, cliente);
		}
	}

	private EnvioDeMensagem criaMensagem(LocalDateTime dataEspecifica, Cliente cliente) {
		Mensagem mensagem = new Mensagem();
		mensagem.setConteudo(conteudo);
		mensagem.setDateHoraDeEnvio(dataEspecifica);
		mensagem.setTipoDeEnvio(TipoEnvio.SMS);
		return EnvioDeMensagem.criarEnvio(cliente, mensagem, provedor);
	}
}
