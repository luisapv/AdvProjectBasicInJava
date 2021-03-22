package popula;

import br.com.project.dao.CepDAO;
import br.com.project.dao.EnderecoDAO;
import br.com.project.dao.PessoaFisicaDAO;
import br.com.project.modelo.Endereco;
import br.com.project.modelo.PessoaFisica;

public class PopulaPessoaFisica {
	
	public static void main(String[] args) {
		PessoaFisica pessoaFisica;
		Endereco endereco;
		for (int i = 0; i < 100; i++) {
			pessoaFisica = new PessoaFisica();
			endereco = new Endereco();
			endereco.setCep(new CepDAO().obterPorCep("25215-245"));
			if (i % 3 == 0) {
				endereco.setNumero("123");
			} else if (i % 2 == 0) {
				endereco.setComplemento("TXT-123");
			} else {
				endereco.setNumero("123");
				endereco.setComplemento("TXT-123");
			}
			pessoaFisica.setNome("Teste " + i);
			pessoaFisica = new PessoaFisicaDAO().salvarReturn(pessoaFisica);
			endereco.setPessoa(pessoaFisica);
			new EnderecoDAO().salvar(endereco);
		}
		
	}
}
