package br.com.project.servico;

import java.util.List;

import br.com.project.modelo.Bairro;
import br.com.project.modelo.Logradouro;

public interface LogradouroServico {

	public void salvar(Logradouro logradouro);
	public Logradouro salvarReturn(Logradouro logradouro);
	public void excluir(Logradouro logradouro);
	public List<Logradouro> listarTodos();
	public Logradouro obterPorId(Long id);
	public List<Logradouro> obterLogradourosDoBairro(Bairro bairro);
	public Logradouro verificarLogradouro(Logradouro logradouro);
	public Logradouro verificarLogradouro(Bairro bairro, String logradouroNome);
	public List<br.com.project.modelo.dataTable.Logradouro> getLazyLogradouroDataTable();
}
