package br.com.project.servico;

import java.util.List;

import br.com.project.modelo.Endereco;
import br.com.project.modelo.Pessoa;

public interface EnderecoServico {

	public void salvar(Endereco endereco);
	public Endereco salvarReturn(Endereco endereco);
	public void excluir(Endereco endereco);
	public void excluirPorPessoa(Pessoa pessoa);
	public List<Endereco> listarTodos();
	public List<Endereco> obterPorPessoa(Pessoa pessoa);
	public Endereco obterPorId(Long id);
	public Endereco obterUmEnderecoPorPessoa(Pessoa pessoa);
	public List<br.com.project.modelo.dataTable.Endereco> getLazyEnderecoDataTable();
}
