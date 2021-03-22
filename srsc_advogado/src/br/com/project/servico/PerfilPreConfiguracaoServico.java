package br.com.project.servico;

import java.util.List;

import br.com.project.modelo.EnumPaginas;
import br.com.project.modelo.Login;
import br.com.project.modelo.PerfilPreConfiguracao;

public interface PerfilPreConfiguracaoServico {

	public void salvar(PerfilPreConfiguracao perfilPreConfiguracao);
	public void excluir(PerfilPreConfiguracao perfilPreConfiguracao);
	public List<PerfilPreConfiguracao> listarTodos();
	public PerfilPreConfiguracao obterPorId(Long id);
	
	public List<PerfilPreConfiguracao> findByLoginPage(Login login, EnumPaginas pagina);
}
