package br.com.project.dao.JDBC;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LogradouroDAO extends GenericDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<br.com.project.modelo.dataTable.Logradouro> getLazyLogradouroDataTale() throws SQLException {
		String sql = "select "
				+"l.id, trim(concat_ws(' ', l.tipoLogradouro, l.nome)) as nome, "
				+ "concat_ws(' \\\\ ',b.nome, concat_ws(' \\\\ ',c.nome,c.estado)) as bairroCidadeUf "
				+"from logradouro as l "
				+"left join bairro as b on l.bairro=b.id "
				+"left join cidade as c on b.cidade=c.id";
		List<br.com.project.modelo.dataTable.Logradouro> logradouros = new ArrayList<br.com.project.modelo.dataTable.Logradouro>();
		br.com.project.modelo.dataTable.Logradouro logradouro = null;

		ResultSet result = getFindSql(sql);

		while (result.next()) {
			logradouro = new br.com.project.modelo.dataTable.Logradouro();

			logradouro.setId(result.getLong("id"));
			logradouro.setNome(result.getString("nome"));
			logradouro.setBairroCidadeUf(result.getString("bairroCidadeUf"));

			logradouros.add(logradouro);
		}

		return logradouros;
	}

}
