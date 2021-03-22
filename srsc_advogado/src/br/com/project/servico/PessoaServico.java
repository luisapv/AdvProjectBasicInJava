package br.com.project.servico;

import java.util.List;

import br.com.project.modelo.Pessoa;

public interface PessoaServico {

	public void salvar(Pessoa pessoa);
	public void excluir(Pessoa pessoa);
	public List<Pessoa> listarTodos();
	public Pessoa obterPorId(Long id);
	public List<Pessoa> obterPessoa(String pessoa);
	public List<Pessoa> obterPessoaAutorPorProcesso(String processo);
	public List<Pessoa> obterPessoaReuPorProcesso(String processo);
}
