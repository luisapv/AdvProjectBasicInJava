package br.com.project.servico;

import java.util.List;

public abstract class GenericServico<T> {
	
	void salvar(T t) {
	}
	
	void excluir(T t) {
	}
	
	List<T> listarTodos() {
		return null;
	}
	
	T obterPorId(Integer id) {
		return null;
	}
}
