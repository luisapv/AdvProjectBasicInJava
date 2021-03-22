package br.com.project.relatorio.jasperIreport.malaDireta;

import java.util.ArrayList;
import java.util.List;

import br.com.project.dao.LoginDAO;
import br.com.project.dao.PessoaDAO;
import br.com.project.modelo.Login;
import br.com.project.modelo.Pessoa;

public class MalaDiretaFactory {

	private PessoaDAO pessoaDAO = new PessoaDAO();
	private LoginDAO loginDAO = new LoginDAO();
	
	@SuppressWarnings("unused")
	private List<MalaDireta> load() {
		List<Pessoa> pessoas = pessoaDAO.listarTodos();
		Login login = loginDAO.obterPorId(new Long(1));
		List<MalaDireta> malaDireta = new ArrayList<MalaDireta>();
		MalaDireta malaD = null;
		for (Pessoa pessoa : pessoas) {
			if (!pessoa.getEnderecos().isEmpty() && pessoa.getEnderecos() != null){
				malaD = new MalaDireta();
				malaD.setNome_destinatario(pessoa.getNome());
				malaD.setEndereco_destinatario(pessoa.getEnderecoCompleto());
				malaD.setNome_remetente(login.getNome());
				malaD.setEndereco_remetente(login.getEndereco().getEnderecoCompleto());
				malaDireta.add(malaD);
			}
		}
		return malaDireta;
	}
	
}
