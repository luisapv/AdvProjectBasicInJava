package br.com.project.servico;

import java.util.List;

import br.com.project.modelo.Bairro;
import br.com.project.modelo.Cep;
import br.com.project.modelo.Cidade;
import br.com.project.modelo.EnumEstado;
import br.com.project.modelo.Logradouro;

public interface CepServico {

	public void salvar(Cep cep);
	public Cep salvarReturn(Cep cep);
	public void excluir(Cep cep);
	public List<Cep> listarTodos();
	public Cep obterPorId(Long id);
	public Cep obterPorCep(String cepStr);
	public List<Cep> obterListPorCep(String cepStr);
	public List<Cep> obterListCepInicia(String cepStr);
	public Cep verificarCep(String cep, EnumEstado estado, Cidade cidade, Bairro bairro, Logradouro logradouro);
	public List<br.com.project.modelo.dataTable.Cep> getLazyCepDataTable();
}
