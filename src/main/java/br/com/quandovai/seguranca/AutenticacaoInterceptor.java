package br.com.quandovai.seguranca;

import javax.inject.Inject;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.quandovai.controladores.LoginController;

public class AutenticacaoInterceptor implements Interceptor {
    
    @Inject
    private UsuarioConectado usuarioConectado;

    @Inject
    private Result result;

    @Override
    public void intercept(InterceptorStack stack, ControllerMethod method, Object controllerInstance)
	    throws InterceptionException {
//	boolean estaLogado = usuarioConectado.estaLogado();
//	if (estaLogado) {
//	    stack.next(method, controllerInstance);
//	} else {
//	    result.redirectTo(LoginController.class).loginForm(null, null);
//	}
	stack.next(method, controllerInstance);
    }

    @Override
    public boolean accepts(ControllerMethod method) {
//	boolean acessoPublico = method.containsAnnotation(AcessoPublico.class);
//	return !acessoPublico;
	return false;
    }

}
