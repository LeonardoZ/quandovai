package br.com.quandovai.seguranca;

import javax.inject.Inject;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;

public class DecorateRequest implements Interceptor {

    @Inject
    private UsuarioConectado usuarioConectado;

    @Inject
    private Result result;

    @Override
    public void intercept(InterceptorStack stack, ControllerMethod method, Object controllerInstance)
	    throws InterceptionException {
	// dev purpose
//	usuarioConectado = usuarioConectado == null ? new UsuarioConectado() : usuarioConectado;
//	result.include("nomeUsuarioLogado", usuarioConectado.getPrimeiroNome());
//	stack.next(method, controllerInstance);
    }

    @Override
    public boolean accepts(ControllerMethod method) {
//	return usuarioConectado.estaLogado();
	return false;
    }

}
