package br.com.project.dao.JDBC;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnderecoDAO extends GenericDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<br.com.project.modelo.dataTable.Endereco> getLazyEnderecoDataTale() throws SQLException {
		String sql = "select "
				+ "e.id, p.nome as pessoa, p.documento, e.numero, e.complemento, "
				+ "cep.cep as cep, c.estado as uf, c.nome as cidade, b.nome as bairro, "
				+ "concat_ws(' ', l.tipoLogradouro, l.nome) as logradouro "
				+ "from endereco as e "
				+ "left join pessoa as p on e.pessoa=p.id "
				+ "left join cep as cep on e.cep=cep.id "
				+ "left join cidade as c on cep.cidade=c.id "
				+ "left join bairro as b on cep.bairro=b.id "
				+ "left join logradouro as l on cep.logradouro=l.id";
		List<br.com.project.modelo.dataTable.Endereco> enderecos = new ArrayList<br.com.project.modelo.dataTable.Endereco>();
		br.com.project.modelo.dataTable.Endereco endereco = null;

		ResultSet result = getFindSql(sql);

		while (result.next()) {
			endereco = new br.com.project.modelo.dataTable.Endereco();

			endereco.setId(result.getLong("id"));
			endereco.setPessoa(result.getString("pessoa"));
			endereco.setDocumento(result.getString("documento"));
			endereco.setNumero(result.getString("numero"));
			endereco.setComplemento(result.getString("complemento"));
			endereco.setCep(result.getString("cep"));
			endereco.setUf(result.getString("uf"));
			endereco.setCidade(result.getString("cidade"));
			endereco.setBairro(result.getString("bairro"));
			endereco.setLogradouro(result.getString("logradouro"));

			enderecos.add(endereco);
		}

		return enderecos;
	}

}
