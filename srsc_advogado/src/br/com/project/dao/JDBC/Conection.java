package br.com.project.dao.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conection {

	private Connection connection;
	
	private Connection conect(){
		String dbURL = "jdbc:mysql://localhost:3306/bd_advogado";
		String username = "root";
		String password = "root";
		 
		try {
		 
		    connection = (Connection) DriverManager.getConnection(dbURL, username, password);
		    return connection;
		} catch (SQLException ex) {
		    ex.printStackTrace();
		    return null;
		}
	}

	public Connection getConnection() throws SQLException {
	    if (connection == null || connection.isClosed())
	    	return conect();
	    else
	    	return connection;
	}
	
	public void close() throws SQLException{
		if (!connection.isClosed())
			connection.close();
	}
}
