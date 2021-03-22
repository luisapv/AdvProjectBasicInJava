package br.com.project.dao.recoverBdOld;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.project.modelo.Fase;
import br.com.project.modelo.Login;

public class FasesDAO extends GenericDAO {

	private static final long serialVersionUID = 1L;

	// @Autowired
	br.com.project.dao.LoginDAO loginServico = new br.com.project.dao.LoginDAO();
	// @Autowired
	br.com.project.dao.FaseDAO faseServico = new br.com.project.dao.FaseDAO();

	public ResultSet getResult() throws SQLException {
		ResultSet result = getFindAll("fases", "");
		return result;
	}

	@SuppressWarnings("unused")
	public void carregar() {
		ResultSet result;
		try {
			result = getResult();
			Fase fase;
			Login login;
			while (result.next()) {
				Long id = new Long(result.getInt("idFases"));
				String nome = result.getString("nome");
				String sigla = null;
				String descricao = result.getString("descricao");
				Date dataCadastro = result.getDate("dataCadastro");
				Long idLogin = new Long(result.getInt("idlogin"));

				if (idLogin == null) {
					idLogin = new Long(500);
				}
				login = loginServico.obterPorId(idLogin);

				if (dataCadastro == null || dataCadastro.toString().equals("0000-00-00")) {
					dataCadastro = getDate(new java.util.Date());
				}

				fase = new Fase();
				fase.setId(id);
				fase.setNome(nome);
				if (sigla == null || sigla.isEmpty())
					sigla = nome;
				fase.setSigla(sigla);
				fase.setDescricao(descricao);
				fase.setDataCadastro(dataCadastro);
				fase.setLogin(login);
				faseServico.salvar(fase);

				// PreparedStatement preparedStmt;
				//
				// preparedStmt = saveNew("fase",
				// "`id`,`nome`,`dataCadastro`,`deletado`,`login`",
				// "?,?,?,?,?");
				// preparedStmt.setLong(1, id);
				// preparedStmt.setString(2, nome);
				// preparedStmt.setDate(3, dataCadastro);
				// preparedStmt.setBoolean(4, false);
				// preparedStmt.setLong(5, idLogin);
				// preparedStmt.execute();
			}
		} catch (SQLException e) {
			System.out.println("FASE");
			e.printStackTrace();
		}
	}
}
