package br.com.project.dao.recoverBdOld;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;

import br.com.project.modelo.EnumEstado;

public abstract class GenericDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<br.com.project.modelo.EnumEstado> estados = Arrays.asList(br.com.project.modelo.EnumEstado.values());
	
	private Connection connNew() throws SQLException{
		return new br.com.project.dao.JDBC.Conection().getConnection();
	}
	
	private Connection connOld = new Conection().getConnectionOld();

	protected java.sql.Date getDate(java.util.Date date){
		java.util.Date d = date;
		java.sql.Date dt = new java.sql.Date (d.getTime());
		return dt;
	}
	
	protected Date validaData(String dataStr) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.sql.Date data = new java.sql.Date(format.parse(dataStr).getTime());
	return data;
	}
	
	protected String getEstado(Integer idEstado) {
		Integer count = 0;
		EnumEstado uf = null;
		for (br.com.project.modelo.EnumEstado estado1 : estados) {
			count++;
			if (idEstado.equals(count)) {
				uf = estado1;
			}
		}
		return uf.toString();
	}
	
	protected EnumEstado getEnumEstado(Integer idEstado) {
		Integer count = 0;
		EnumEstado uf = null;
		for (br.com.project.modelo.EnumEstado estado1 : estados) {
			count++;
			if (idEstado.equals(count)) {
				uf = estado1;
			}
		}
		return uf;
	}

	protected ResultSet getFindAll(String tabela, String where) throws SQLException {
		String sql = "SELECT * FROM " + tabela + where;

		Statement statement = connOld.createStatement();
		ResultSet result = statement.executeQuery(sql);
		return result;
	}

	protected ResultSet getSql(String sql) throws SQLException {
		Statement statement = connOld.createStatement();
		ResultSet result = statement.executeQuery(sql);
		return result;
	}

	protected void save(String tabela, String colluns, String values) throws SQLException {
		String sql = "INSERT INTO " + tabela + "(" + colluns + ") VALUES(" + values + ")";

		Statement statement = connOld.createStatement();
		statement.executeQuery(sql);
	}

	protected ResultSet getFindAllNew(String tabela, String where) throws SQLException {
		String sql = "SELECT * FROM " + tabela + where;

		Statement statement = connNew().createStatement();
		ResultSet result = statement.executeQuery(sql);
		return result;
	}

	protected ResultSet getSqlNew(String sql) throws SQLException {
		Statement statement = connNew().createStatement();
		ResultSet result = statement.executeQuery(sql);
		return result;
	}

	protected PreparedStatement saveNew(String tabela, String colluns, String values) throws SQLException {
		String sql = "INSERT INTO " + tabela + "(" + colluns + ") VALUES(" + values + ")";

		// Statement statement = new
		// br.com.project.dao.JDBC.Conection().getConnection().createStatement();
		// statement.executeQuery(sql);
		
		
		PreparedStatement preparedStmt = (PreparedStatement) connNew().prepareStatement(sql);
		return preparedStmt;
	}
}
