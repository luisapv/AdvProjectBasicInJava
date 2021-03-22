package br.com.project.control.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.project.autenticacao.AuthenticationContext;
import br.com.project.control.LoginController;
import br.com.project.modelo.EnumPaginas;
import br.com.project.modelo.Login;
import br.com.project.modelo.PerfilPreConfiguracao;
import br.com.project.servico.PerfilPreConfiguracaoServico;

@Service(value = "loginController")
@Transactional
public class LoginControllerImpl implements LoginController {

	@Autowired
	private AuthenticationContext authenticationContext;

	@Autowired
	private PerfilPreConfiguracaoServico perfilPreConfiguracaoServico;

	@Override
	public PerfilPreConfiguracao obterPerfilPreConfiguracaoLoginPage(EnumPaginas pagina) {
		return (PerfilPreConfiguracao) perfilPreConfiguracaoServico.findByLoginPage(this.obterLoginLogado(), pagina);
	}

	@Override
	public Login obterLoginLogado() {
		return authenticationContext.getLoginLogado();
	}

	public AuthenticationContext getAuthenticationContext() {
		return authenticationContext;
	}

	public void setAuthenticationContext(AuthenticationContext authenticationContext) {
		this.authenticationContext = authenticationContext;
	}

	public PerfilPreConfiguracaoServico getPerfilPreConfiguracaoServico() {
		return perfilPreConfiguracaoServico;
	}

	public void setPerfilPreConfiguracaoServico(PerfilPreConfiguracaoServico perfilPreConfiguracaoServico) {
		this.perfilPreConfiguracaoServico = perfilPreConfiguracaoServico;
	}
}
