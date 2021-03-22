package br.com.project.dao.JDBC;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BairroDAO extends GenericDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<br.com.project.modelo.dataTable.Bairro> getLazyBairroDataTale() throws SQLException {
		String sql = "select "
				+ "b.id, b.nome, concat_ws(' \\\\ ',c.nome,c.estado) as cidadeUf"
				+ " from bairro as b left join cidade as c on b.cidade=c.id";
		List<br.com.project.modelo.dataTable.Bairro> bairros = new ArrayList<br.com.project.modelo.dataTable.Bairro>();
		br.com.project.modelo.dataTable.Bairro bairro = null;

		ResultSet result = getFindSql(sql);

		while (result.next()) {
			bairro = new br.com.project.modelo.dataTable.Bairro();

			bairro.setId(result.getLong("id"));
			bairro.setNome(result.getString("nome"));
			bairro.setCidadeUf(result.getString("cidadeUf"));

			bairros.add(bairro);
		}

		return bairros;
	}

}
