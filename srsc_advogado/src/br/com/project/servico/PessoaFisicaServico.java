package br.com.project.servico;

import java.util.List;

import br.com.project.modelo.PessoaFisica;

public interface PessoaFisicaServico {

	public void salvar(PessoaFisica pessoaFisica);
	public PessoaFisica salvarReturn(PessoaFisica pessoaFisica);
	public void excluir(PessoaFisica pessoaFisica);
	public List<PessoaFisica> listarTodos();
	public List<PessoaFisica> getLazyDataModel(int first, int pageSize);
	public Long getMaxResult();
	public PessoaFisica obterPorId(Long id);
	public List<PessoaFisica> obterPessoaFisica(String pessoaFisica);
	public List<String> obterProfissoes();
	public List<String> obterProfissao(String profissao);
	public List<br.com.project.modelo.dataTable.PessoaFisica> getLazyPessoaFisicaDataTable();
}
