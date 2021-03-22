package br.com.project.servico;

import java.util.List;

import br.com.project.modelo.Login;

public interface LoginServico {

	public void salvar(Login login);
	public Login salvarReturn(Login login);
	public void excluir(Login login);
	public List<Login> listarTodos();
	public Login obterPorId(Long id);
	public Login obterLoginPeloLogin(String login);
	public List<br.com.project.modelo.dataTable.Login> getLazyLoginDataTable();
}
