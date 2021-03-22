package br.com.project.dao.recoverBdOld;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.project.modelo.Login;
import br.com.project.modelo.PessoaJuridica;


public class EmpresaDAO extends GenericDAO {

	private static final long serialVersionUID = 1L;
	
//	private EnderecoDAO enderecoDAO = new EnderecoDAO();
	
//	@Autowired
	br.com.project.dao.PessoaJuridicaDAO pessoaJuridicaServico = new br.com.project.dao.PessoaJuridicaDAO();
//	@Autowired
	br.com.project.dao.LoginDAO loginServico = new br.com.project.dao.LoginDAO();

	public ResultSet getResult() throws SQLException {
		ResultSet result = getFindAll("empresa", "");
		return result;
	}
	
	@SuppressWarnings("unused")
	public void carregar() {
		ResultSet result;
		try {
			result = getResult();
			PessoaJuridica pessoaJurdica;
			Login login;
			while (result.next()) {
				Long id = new Long(result.getInt("cod_empresa")*3-1);
				String nome = result.getString("razao_social");
				String apelido = result.getString("nome_fantasia");
				String documento = result.getString("cnpj");
				Date dataNascimento = null;
				String email = result.getString("email");
				String observacao  = result.getString("observacao");
				Boolean deletado = false;
				Date dataDeletado = null;
				Date dataCadastro = result.getDate("dataCadastro");
				Long idlogin  = new Long(result.getInt("idLogin"));
				String pessoaResponsavel = result.getString("pessoa_responsavel");
				
				String numero = result.getString("numero");
				String complemento = result.getString("complemento");
				String referencia = result.getString("referencia");
				String cep = result.getString("cep");
				Integer estado = result.getInt("municipio");
				String cidade = result.getString("cidade");
				String bairro = result.getString("bairro");
				String logradouro = result.getString("logradouro");
				
				if (idlogin == null){
					idlogin = new Long(500);
				}
				login = loginServico.obterPorId(idlogin); 
				
				if (dataCadastro == null || dataCadastro.toString().equals("0000-00-00")){
					dataCadastro = getDate(new java.util.Date());
				}
				
				pessoaJurdica = new PessoaJuridica();
				pessoaJurdica.setId(id);
				pessoaJurdica.setNome(nome);
				pessoaJurdica.setApelido(apelido);
				pessoaJurdica.setDocumento(documento);
				pessoaJurdica.setEmail(email);
				pessoaJurdica.setObservacao(observacao);
				pessoaJurdica.setDeletado(false);
				pessoaJurdica.setDataCadastro(dataCadastro);
				pessoaJurdica.setLogin(login);
				pessoaJurdica.setPessoaResponsavel(pessoaResponsavel);
				pessoaJurdica = pessoaJuridicaServico.salvarReturn(pessoaJurdica);
				
//				enderecoDAO.carregarEnderecoPessoa(estado, cidade, bairro, logradouro, cep, numero, complemento, referencia, pessoaJurdica);
			}
		} catch (SQLException e) {
			System.out.println("EMPRESA");
			e.printStackTrace();
		}
	}
}
