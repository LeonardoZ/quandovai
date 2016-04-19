package br.com.quandovai.controladores;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.quandovai.daos.UsuarioDao;
import br.com.quandovai.modelo.Login;
import br.com.quandovai.modelo.UsuarioCadastro;
import br.com.quandovai.modelo.entidade.Papel;
import br.com.quandovai.modelo.entidade.Usuario;
import br.com.quandovai.seguranca.AcessoPublico;
import br.com.quandovai.seguranca.CriptografiaSenha;
import br.com.quandovai.seguranca.UsuarioConectado;

@Controller
public class LoginController {

	@Inject
	private Validator validator;

	@Inject
	private UsuarioDao usuarioDAO;

	@Inject
	private Result result;

	@Inject
	private UsuarioConectado usuarioConectado;

	@AcessoPublico
	@Get("/login")
	public void loginForm(UsuarioCadastro usuario, Login login) {
		result.include("usuarioCadastro", usuario);
		result.include("login", login);
	}

	@AcessoPublico
	@Post("/usuario")
	@Transactional
	public void criarUsuario(UsuarioCadastro usuarioCadastro) {
		validator.validate(usuarioCadastro)
				.addIf(usuarioCadastro.senhasDiferentes(),
						new SimpleMessage("CadastroUsuario", "Senhas devem coincidir"))
				.onErrorForwardTo(this).loginForm(usuarioCadastro, null);
		String senha = usuarioCadastro.getSenha();
		byte[] salt = CriptografiaSenha.gerarSalt();
		byte[] encriptado = CriptografiaSenha.pegaSenhaCriptografada(senha, salt);

		Usuario usuario = new Usuario();
		usuario.setSenhaHash(encriptado);
		usuario.setSalt(salt);
		usuario.setNome(usuarioCadastro.getNome());
		usuario.setEmail(usuarioCadastro.getEmail());
		usuario.getPapeis().add(Papel.USUARIO);

		usuarioDAO.salvar(usuario);

		result.include("mensagem-sucesso", "Usuário cadastrado com sucesso!");
		result.redirectTo(this).loginForm(UsuarioCadastro.vazio(), new Login(usuario.getEmail(), ""));
	}

	@AcessoPublico
	@Post("/login")
	public void login(@NotNull Login login) {
		validator.onErrorForwardTo(this).loginForm(null, login);
		Usuario carregado = usuarioDAO.buscaPorEmail(login.getEmail());
		if (carregado == null) {
			validator.add(new SimpleMessage("login", "Usuário e/ou senhas inválidos")).onErrorRedirectTo(this)
					.loginForm(null, login);
		}
		boolean autenticado = CriptografiaSenha.autenticar(login.getSenha(), carregado.getSenhaHash(),
				carregado.getSalt());

		if (!autenticado) {
			validator.add(new SimpleMessage("login", "Usuário e/ou senhas inválidos")).onErrorRedirectTo(this)
					.loginForm(null, login);
		} else {
			usuarioConectado.setEmail(carregado.getEmail());
			usuarioConectado.setNome(carregado.getNome());
			usuarioConectado.confirmaLogado();
			result.redirectTo(PrincipalController.class).index();
		}
	}

	@Get("/logout")
	public void logout() {
		usuarioConectado.deslogar();
	}

}
