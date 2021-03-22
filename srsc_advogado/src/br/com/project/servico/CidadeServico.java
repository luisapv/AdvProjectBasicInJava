package br.com.project.servico;

import java.util.List;

import br.com.project.modelo.Cidade;
import br.com.project.modelo.EnumEstado;

public interface CidadeServico {

	public void salvar(Cidade cidade);
	public Cidade salvarReturn(Cidade cidade);
	public void excluir(Cidade cidade);
	public List<Cidade> listarTodos();
	public Cidade obterPorId(Long id);
	public List<Cidade> obterCidadesDoEstado(EnumEstado estado);
	public Cidade verificarCidade(Cidade cidade);
	public Cidade verificarCidade(EnumEstado estado, String cidade);
	
}
