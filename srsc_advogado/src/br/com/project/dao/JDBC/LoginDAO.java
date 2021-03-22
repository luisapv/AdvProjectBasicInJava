package br.com.project.dao.JDBC;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginDAO extends GenericDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<br.com.project.modelo.dataTable.Login> getLazyLoginDataTale() throws SQLException {
		String sql = "select login.id, login.nome, login.login, login.senha, login.email, login.tema, login.localSave, login.ativo, "
				+ "case when login.endereco <> '' then concat_ws(' ', l.tipoLogradouro, concat_ws('', l.nome, "
				+ "concat_ws(', ', case when e.numero<>null and e.complemento<>null then concat_ws(', ',e.numero,' - ',e.complemento) "
				+ "when e.numero<>null and e.complemento=null then concat_ws(', ',e.numero) when e.numero=null and e.complemento<>null then "
				+ "concat_ws(', ',e.complemento) else '' end, concat_ws(', ', b.nome, concat_ws('/', c.nome, concat_ws(' - ',c.estado, cep.cep)))))) "
				+ "else '' end as enderecoCompleto, login.dataCadastro "
				+ "from login "
				+ "left join endereco as e on e.id = login.endereco "
				+ "left join cep on e.cep=cep.id "
				+ "left join cidade as c on cep.cidade=c.id "
				+ "left join bairro as b on cep.bairro=b.id "
				+ "left join logradouro as l on cep.logradouro=l.id";
		List<br.com.project.modelo.dataTable.Login> logins = new ArrayList<br.com.project.modelo.dataTable.Login>();
		br.com.project.modelo.dataTable.Login login = null;

		ResultSet result = getFindSql(sql);

		while (result.next()) {
			login = new br.com.project.modelo.dataTable.Login();

			login.setId(result.getLong("id"));
			login.setNome(result.getString("nome"));
			login.setLogin(result.getString("login"));
			login.setEmail(result.getString("email"));
			login.setTema(result.getString("tema"));
			login.setLocalSave(result.getString("localSave"));
			login.setAtivo(result.getBoolean("ativo"));
			login.setEnderecoCompleto(result.getString("enderecoCompleto"));
			login.setDataCadastro(result.getDate("dataCadastro"));

			logins.add(login);
		}

		return logins;
	}

}
