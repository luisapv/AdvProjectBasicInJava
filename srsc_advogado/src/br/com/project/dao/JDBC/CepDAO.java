package br.com.project.dao.JDBC;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CepDAO extends GenericDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<br.com.project.modelo.dataTable.Cep> getLazyCepDataTale() throws SQLException {
		String sql = "select "
				+ "cep.id, cep.cep as cep, c.estado as uf, c.nome as cidade, b.nome as bairro, "
				+ "concat_ws(' ', l.tipoLogradouro, l.nome) as logradouro "
				+ "from cep as cep "
				+ "left join cidade as c on cep.cidade=c.id "
				+ "left join bairro as b on cep.bairro=b.id "
				+ "left join logradouro as l on cep.logradouro=l.id";
		List<br.com.project.modelo.dataTable.Cep> ceps = new ArrayList<br.com.project.modelo.dataTable.Cep>();
		br.com.project.modelo.dataTable.Cep cep = null;

		ResultSet result = getFindSql(sql);

		while (result.next()) {
			cep = new br.com.project.modelo.dataTable.Cep();

			cep.setId(result.getLong("id"));
			cep.setCep(result.getString("cep"));
			cep.setUf(result.getString("uf"));
			cep.setCidade(result.getString("cidade"));
			cep.setBairro(result.getString("bairro"));
			cep.setLogradouro(result.getString("logradouro"));

			ceps.add(cep);
		}

		return ceps;
	}

}
