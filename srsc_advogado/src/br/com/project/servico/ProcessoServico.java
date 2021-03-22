package br.com.project.servico;

import java.util.List;

import br.com.project.modelo.Processo;

public interface ProcessoServico {

	public void salvar(Processo processo);
	public void excluir(Processo processo);
	public List<Processo> listarTodos();
	public Processo obterPorId(Processo processo);
	public Processo obterPorId(String processo);
	public List<String> obterJuizes(String juiz);
	public List<String> obterTrtRelator(String trtRelator);
	public List<String> obterTstRelator(String tstRelator);
	public Processo salvarReturn(Processo processo);
	public List<br.com.project.modelo.dataTable.Processo> getLazyProcessoDataTable();
}
