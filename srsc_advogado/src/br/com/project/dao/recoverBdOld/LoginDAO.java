package br.com.project.dao.recoverBdOld;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import br.com.project.modelo.Endereco;
import br.com.project.modelo.EnumTema;
import br.com.project.modelo.Login;

public class LoginDAO extends GenericDAO {

	private static final long serialVersionUID = 1L;

	// @Autowired
	br.com.project.dao.LoginDAO loginServico = new br.com.project.dao.LoginDAO();

	public ResultSet getResult() throws SQLException {
		ResultSet result = getFindAll("login", "");
		return result;
	}

	public void carregar() {
		ResultSet result;

		try {
			Login login;

			if (loginServico.obterPorId(new Long(501)) == null) {
				login = new Login();
				login.setId(new Long(501));
				login.setAtivo(true);
				login.setDataCadastro(new Date());
				login.setLogin("admin");
				login.setNome("Admiistrador");
				login.setSenha("8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918");
				login.setTema(EnumTema.afternoon);
				loginServico.salvar(login);
			}
			login = null;

			if (loginServico.obterPorId(new Long(500)) == null) {
				login = new Login();
				login.setId(new Long(500));
				login.setAtivo(true);
				login.setDataCadastro(new Date());
				login.setLogin("system");
				login.setNome("Sistema");
				login = loginServico.salvarReturn(login);
			}
			login = null;

			String sql = "select e.numero, e.complemento, e.referencia, cep.cep, cep.cod_municipio as estado, c.nome as cidade, b.nome as bairro, l.nome as logradouro from endereco as e left join cep as cep on e.cod_cep=cep.cod_cep left join cidade as c on cep.cod_cidade=c.cod_cidade left join bairro as b on cep.cod_bairro=b.cod_bairro left join logradouro as l on cep.cod_logradouro=l.cod_logradouro where e.cod_endereco=1";
			result = getSql(sql);
			Endereco idEndereco = null;
			while (result.next()) {
				idEndereco = new EnderecoParteDAO().carregarEnderecoLogin(result.getInt("estado"),
						result.getString("cidade"), result.getString("bairro"), result.getString("logradouro"),
						result.getString("cep"), result.getString("numero"), result.getString("complemento"), null);
			}

			result = getResult();
			while (result.next()) {

				login = new Login();
				login.setId(new Long(result.getInt("idlogin")));
				login.setAtivo(true);
				login.setDataCadastro(new Date());
				login.setLocalSave("E:\\");
				login.setLogin(result.getString("nome"));
				login.setNome(result.getString("nome"));
				login.setSenha("46070d4bf934fb0d4b06d9e2c46e346944e322444900a435d7d9a95e6d7435f5");
				login.setTema(EnumTema.afternoon);
				login.setEndereco(idEndereco);
				loginServico.salvar(login);
			}
		} catch (SQLException e) {
			System.out.println("LOGIN");
			e.printStackTrace();
		}
	}
}
