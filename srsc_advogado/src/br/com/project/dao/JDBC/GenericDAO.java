package br.com.project.dao.JDBC;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class GenericDAO extends Conection implements Serializable {
	
	private static final long serialVersionUID = 1L;

	protected ResultSet getFindAll(String tabela, String where) throws SQLException {
		String sql = "SELECT * FROM " + tabela + where;
		 
		Statement statement = getConnection().createStatement();
		ResultSet result = statement.executeQuery(sql);
		return result;
	}

	protected ResultSet getFindSql(String sql) throws SQLException {
		Statement statement = getConnection().createStatement();
		ResultSet result = statement.executeQuery(sql);
		return result;
	}

	protected void save(String tabela, String colluns, String values) throws SQLException {
		String sql = "INSERT INTO " + tabela + "(" + colluns + ") VALUES(" + values + ")";
		
		Connection conn = getConnection();
		PreparedStatement preparedStmt = conn.prepareStatement(sql);
		preparedStmt.execute();
		
		close();
	}
}
