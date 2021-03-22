package br.com.project.dao.JDBC;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdvogadoDAO extends GenericDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<br.com.project.modelo.dataTable.Advogado> getLazyAdvogadoDataTale() throws SQLException {
		String sql = "select "
				+"p.id, p.nome, concat(pf.identidade,'/',pf.estadoIdentidade) as oab, "
				+"pf.dataExpedicaoIdentidade, login.nome as cadastradoPor, p.dataCadastro "
				+"from advogado as a "
				+"left join pessoafisica as pf on pf.id=a.id "
				+"left join pessoa as p on p.id=pf.id "
				+"left join login on p.login=login.id;";
		List<br.com.project.modelo.dataTable.Advogado> advogados = new ArrayList<br.com.project.modelo.dataTable.Advogado>();
		br.com.project.modelo.dataTable.Advogado advogado = null;

		ResultSet result = getFindSql(sql);

		while (result.next()) {
			advogado = new br.com.project.modelo.dataTable.Advogado();

			advogado.setId(result.getLong("id"));
			advogado.setNome(result.getString("nome"));
			advogado.setOab(result.getString("oab"));
			advogado.setDataExpedicaoAob(result.getDate("dataExpedicaoIdentidade"));
			advogado.setCadastradoPor(result.getString("cadastradoPor"));
			advogado.setDataCadastro(result.getDate("dataCadastro"));

			advogados.add(advogado);
		}

		return advogados;
	}

}
