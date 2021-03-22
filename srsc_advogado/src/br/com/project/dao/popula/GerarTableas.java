package br.com.project.dao.popula;

import java.util.List;

import br.com.project.dao.CidadeDAO;
import br.com.project.modelo.Cidade;

public class GerarTableas {
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		List<Cidade> cidades = new CidadeDAO().listarTodos();
	}	
}
