package br.com.project.servico;

import java.util.List;

import br.com.project.modelo.PerfilConfiguracao;

public interface PerfilConfiguracaoServico {

	public void salvar(PerfilConfiguracao perfilConfiguracao);
	public void excluir(PerfilConfiguracao perfilConfiguracao);
	public List<PerfilConfiguracao> listarTodos();
	public PerfilConfiguracao obterPorId(Long id);
}
