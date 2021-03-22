package br.com.project.dao.JDBC;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PessoaFisicaDAO extends GenericDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<br.com.project.modelo.dataTable.PessoaFisica> getLazyPessoaFisicaDataTale() throws SQLException {
		String sql = "select "
				+ "p.id, p.nome, p.documento, p.dataNascimento, pf.identidade, concat(pf.orgoao,'/',pf.estadoIdentidade) as orgaoUf, "
				+ "pf.dataExpedicaoIdentidade, p.email, pf.profissao, "
				+ "case "
				+ "when e.pessoa <> '' then "
				+ "concat_ws(' ', l.tipoLogradouro, "
				+ "concat_ws('', l.nome, "
				+ "concat_ws(', ', case when e.numero<>null and e.complemento<>null then "
				+ "concat_ws(', ',e.numero,' - ',e.complemento) when e.numero<>null and e.complemento=null then "
				+ "concat_ws(', ',e.numero) when e.numero=null and e.complemento<>null then "
				+ "concat_ws(', ',e.complemento) else '' end, "
				+ "concat_ws(', ', b.nome, concat_ws('/', c.nome, "
				+ "concat_ws(' - ',c.estado, cep.cep)))))) else '' end as enderecoCompleto, "
				+ "login.nome as cadastradoPor, p.dataCadastro "
				+ "from pessoafisica as pf "
				+ "left join pessoa as p on p.id=pf.id "
				+ "left join endereco as e on p.id=e.pessoa "
				+ "left join cep on e.cep=cep.id "
				+ "left join cidade as c on cep.cidade=c.id "
				+ "left join bairro as b on cep.bairro=b.id "
				+ "left join logradouro as l on cep.logradouro=l.id "
				+ "left join login on p.login=login.id;";
		List<br.com.project.modelo.dataTable.PessoaFisica> pessoaFisicas = new ArrayList<br.com.project.modelo.dataTable.PessoaFisica>();
		br.com.project.modelo.dataTable.PessoaFisica pessoaFisica = null;

		ResultSet result = getFindSql(sql);

		while (result.next()) {
			pessoaFisica = new br.com.project.modelo.dataTable.PessoaFisica();

			pessoaFisica.setId(result.getLong("id"));
			pessoaFisica.setNome(result.getString("nome"));
			pessoaFisica.setDocumento(result.getString("documento"));
			pessoaFisica.setDataNascimento(result.getDate("dataNascimento"));
			pessoaFisica.setIdentidade(result.getString("identidade"));
			pessoaFisica.setOrgoaoUf(result.getString("orgaoUf"));
			pessoaFisica.setDataExpedicao(result.getDate("dataExpedicaoIdentidade"));
			pessoaFisica.setEmail(result.getString("email"));
			pessoaFisica.setProfissao(result.getString("profissao"));
			pessoaFisica.setEnderecoCompleto(result.getString("enderecoCompleto"));
			pessoaFisica.setCadastradoPor(result.getString("cadastradoPor"));
			pessoaFisica.setDataCadastro(result.getDate("dataCadastro"));

			pessoaFisicas.add(pessoaFisica);
		}

		return pessoaFisicas;
	}

}
