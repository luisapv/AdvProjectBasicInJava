package br.com.project.servico;

import java.util.List;

import br.com.project.modelo.Fase;

public interface FaseServico {

	public void salvar(Fase fase);
	public Fase salvarReturn(Fase faseNovo);
	public void excluir(Fase fase);
	public List<Fase> listarTodos();
	public Fase obterPorId(Long id);
	public List<Fase> obterFase(String fase);
	public List<Fase> obterPorProcesso(String processo);
}
