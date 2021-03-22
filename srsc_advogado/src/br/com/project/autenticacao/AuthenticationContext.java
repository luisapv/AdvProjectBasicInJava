package br.com.project.autenticacao;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.project.modelo.Login;

@Component("authenticationContext")
public class AuthenticationContext {
	
	public Login getLoginLogado(){
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
	
		if(authentication == null){
			return null;
		}
		return (Login) authentication.getPrincipal();
	}
	
	public void logout() throws IOException{
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		((HttpSession)context.getSession(false)).invalidate();
		context.redirect(context.encodeResourceURL(((HttpServletRequest)context.getRequest()).getContextPath()+"/j_spring_security_logout"));
	}
}
