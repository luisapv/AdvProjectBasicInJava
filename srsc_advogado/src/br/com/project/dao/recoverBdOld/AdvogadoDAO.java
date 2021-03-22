package br.com.project.dao.recoverBdOld;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import br.com.project.modelo.Advogado;
import br.com.project.modelo.Login;

public class AdvogadoDAO extends GenericDAO {

	private static final long serialVersionUID = 1L;

	// @Autowired
	br.com.project.dao.AdvogadoDAO advogadoServico = new br.com.project.dao.AdvogadoDAO();;
	// @Autowired
	br.com.project.dao.LoginDAO loginServico = new br.com.project.dao.LoginDAO();

	private ResultSet getResult() throws SQLException {
		ResultSet result = getFindAll("advogado", "");
		return result;
	}

	@SuppressWarnings("unused")
	public void carregar() {
		ResultSet result;
		try {
			result = getResult();

			Advogado advogado;
			Login login;

			while (result.next()) {
				Long id = new Long(result.getInt("cod_advogado") * 3);
				String nome = result.getString("nome");
				String oab = result.getString("oab");
				String estado = getEstado(result.getInt("estado"));
				Date dataExpedicao = result.getDate("data_da_expedicao");
				Date dataCadastro = result.getDate("dataCadastro");
				Long idLogin = new Long(result.getInt("idlogin"));

				if (idLogin == null) {
					idLogin = new Long(500);
				}
				login = loginServico.obterPorId(idLogin);

				if (dataCadastro == null || dataCadastro.toString().equals("0000-00-00")) {
					dataCadastro = new Date();
				}
				
				if (dataExpedicao != null)
				if (dataExpedicao.toString().equals("0000-00-00")) {
					dataExpedicao = null;
				}

				advogado = new Advogado();

				advogado.setId(id);
				advogado.setNome(nome);
				advogado.setIdentidade(oab);
				advogado.setEstadoIdentidade(getEnumEstado(result.getInt("estado")));
				advogado.setDataExpedicaoIdentidade(dataExpedicao);
				advogado.setDataCadastro(dataCadastro);
				advogado.setLogin(login);

				advogadoServico.salvar(advogado);

				// PreparedStatement preparedStmt;
				//
				// preparedStmt = saveNew("pessoa",
				// "`id`,`nome`,`dataCadastro`,`deletado`,`login`",
				// "?,?,?,?,?");
				// preparedStmt.setLong(1, id);
				// preparedStmt.setString(2, nome);
				// preparedStmt.setDate(3, dataCadastro);
				// preparedStmt.setBoolean(4, false);
				// preparedStmt.setLong(5, idLogin);
				// preparedStmt.execute();
				//
				// preparedStmt =
				// saveNew("pessoafisica","`dataExpedicaoIdentidade`,`estadoIdentidade`,`identidade`,`orgoao`,`profissao`,`id`",
				// "?,?,?,?,?,?");
				// preparedStmt.setDate(1, (java.sql.Date) dataExpedicao);
				// preparedStmt.setString(2, estado);
				// preparedStmt.setString(3, oab);
				// preparedStmt.setString(4, "OAB");
				// preparedStmt.setString(5, "ADVOGADO");
				// preparedStmt.setLong(6, id);
				// preparedStmt.execute();
				//
				// preparedStmt = saveNew("advogado", "id", "?");
				// preparedStmt.setLong(1, id);
				// preparedStmt.execute();
			}
		} catch (SQLException e) {
			System.out.println("ADVOGADO");
			e.printStackTrace();
		}
	}
}
