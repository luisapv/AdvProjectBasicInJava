package br.com.project.control;

import br.com.project.modelo.EnumPaginas;
import br.com.project.modelo.Login;
import br.com.project.modelo.PerfilPreConfiguracao;

public interface LoginController {

	PerfilPreConfiguracao obterPerfilPreConfiguracaoLoginPage(EnumPaginas pagina);
	Login obterLoginLogado();
}
