package br.com.project.dao.recoverBdOld;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.project.modelo.Login;
import br.com.project.modelo.PessoaFisica;

public class PessoaDAO extends GenericDAO {

	private static final long serialVersionUID = 1L;

	// @Autowired
	br.com.project.dao.PessoaFisicaDAO pessoaFisicaServico = new br.com.project.dao.PessoaFisicaDAO();
	// @Autowired
	br.com.project.dao.LoginDAO loginServico = new br.com.project.dao.LoginDAO();

	// private EnderecoDAO enderecoDAO = new EnderecoDAO();

	public ResultSet getResult() throws SQLException {
		ResultSet result = getFindAll("pessoa", "");
		return result;
	}

	@SuppressWarnings("unused")
	public void carregar() {
		ResultSet result;
		try {
			result = getResult();
			Login login;
			PessoaFisica pessoaFisica;
			while (result.next()) {
				Long id = new Long(result.getInt("cod_pessoa") * 3 - 2);
				String nome = result.getString("nome");
				String apelido = null;
				String documento = result.getString("cpf");
				Date dataNascimento = result.getDate("data_nascimento");
				String email = result.getString("email");
				String observacao = result.getString("observacao");
				Boolean deletado = false;
				Date dataDeletado = null;
				Date dataCadastro = result.getDate("dataCadastro");
				Long idlogin = new Long(result.getInt("idLogin"));
				String identidade = result.getString("identidade");
				String orgoao = result.getString("orgoao");
				Integer estadoIdentidade = null;
				Date dataExpedicaoIdentidade = result.getDate("data_da_expedicao");
				String profissao = result.getString("profissao");

				String numero = result.getString("numero");
				String complemento = result.getString("complemento");
				String referencia = result.getString("referencia");
				String cep = result.getString("cep");
				Integer estado = result.getInt("municipio");
				String cidade = result.getString("cidade");
				String bairro = result.getString("bairro");
				String logradouro = result.getString("logradouro");

				if (idlogin == null) {
					idlogin = new Long(500);
				}
				login = loginServico.obterPorId(idlogin);

				if (dataCadastro == null || dataCadastro.toString().equals("0000-00-00")) {
					dataCadastro = getDate(new java.util.Date());
				}

				if (dataNascimento != null)
					if (dataNascimento.toString().equals("0000-00-00")) {
						dataNascimento = null;
					}

				if (dataExpedicaoIdentidade != null)
					if (dataExpedicaoIdentidade.toString().equals("0000-00-00")) {
						dataExpedicaoIdentidade = null;
					}

				pessoaFisica = new PessoaFisica();
				pessoaFisica.setId(id);
				pessoaFisica.setNome(nome);
				pessoaFisica.setDocumento(documento);
				pessoaFisica.setDeletado(false);
				pessoaFisica.setEmail(email);
				pessoaFisica.setObservacao(observacao);
				pessoaFisica.setProfissao(profissao);
				pessoaFisica.setIdentidade(identidade);
				pessoaFisica.setOrgoao(orgoao);
				if (estadoIdentidade != null)
					pessoaFisica.setEstadoIdentidade(getEnumEstado(estadoIdentidade));
				pessoaFisica.setDataExpedicaoIdentidade(dataExpedicaoIdentidade);
				if (dataNascimento != null)
					pessoaFisica.setDataNascimento(dataNascimento);
				pessoaFisica.setDataCadastro(dataCadastro);
				pessoaFisica.setLogin(login);

				pessoaFisicaServico.salvar(pessoaFisica);
				//
				// enderecoDAO.carregarEnderecoPessoa(estado, cidade, bairro,
				// logradouro, cep, numero, complemento,
				// referencia, pessoaFisica);
			}
		} catch (SQLException e) {
			System.out.println("PESSOAS");
			e.printStackTrace();
		}
	}
}
