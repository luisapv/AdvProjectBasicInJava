package br.com.project.servico;

import java.util.List;

import br.com.project.modelo.Bairro;
import br.com.project.modelo.Cidade;

public interface BairroServico {

	public void salvar(Bairro bairro);
	public Bairro salvarReturn(Bairro bairro);
	public void excluir(Bairro bairro);
	public List<Bairro> listarTodos();
	public Bairro obterPorId(Long id);
	public List<Bairro> obterBairrosDaCidade(Cidade cidade);
	public Bairro verificarBairro(Bairro bairro);
	public Bairro verificarBairro(Cidade cidade, String bairroNome);
	public List<br.com.project.modelo.dataTable.Bairro> getLazyBairroDataTable();
}
