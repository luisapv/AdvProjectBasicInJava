package br.com.project.servico;

import java.util.List;

import br.com.project.modelo.Advogado;

public interface AdvogadoServico {

	public void salvar(Advogado advogado);
	public Advogado salvarReturn(Advogado advogado);
	public void excluir(Advogado advogado);
	public List<Advogado> listarTodos();
	public Advogado obterPorId(Long id);
	public List<Advogado> obterAdvogado(String advogado);
	public List<Advogado> obterAdvogadoAutorPorProcesso(String processo);
	public List<Advogado> obterAdvogadoReuPorProcesso(String processo);
	public List<br.com.project.modelo.dataTable.Advogado> getLazyAdvogadoDataTable();
}
