package br.com.project.servico;

import java.util.List;

import br.com.project.modelo.PessoaJuridica;

public interface PessoaJuridicaServico {

	public void salvar(PessoaJuridica pessoaJuridica);
	public PessoaJuridica salvarReturn(PessoaJuridica pessoaJuridica);
	public void excluir(PessoaJuridica pessoaJuridica);
	public List<PessoaJuridica> listarTodos();
	public PessoaJuridica obterPorId(Long id);
	public List<PessoaJuridica> obterPessoaJuridica(String pessoaJuridica);
	public List<br.com.project.modelo.dataTable.PessoaJuridica> getLazyPessoaJuridicaDataTable();
}
