package br.com.project.servico;

import java.util.List;

import br.com.project.modelo.Permissao;

public interface PermissaoServico {

	public void salvar(Permissao permissao);
	public void excluir(Permissao permissao);
	public List<Permissao> listarTodos();
	public Permissao obterPorId(Long id);
}
