package br.com.project.servico;

import java.util.List;

import br.com.project.modelo.Materia;

public interface MateriaServico {

	public void salvar(Materia materia);
	public Materia salvarReturn(Materia materia);
	public void excluir(Materia materia);
	public List<Materia> listarTodos();
	public Materia obterPorId(Long id);
	public List<Materia> obterMateria(String materia);
	public List<Materia> obterPorProcesso(String processo);
}
