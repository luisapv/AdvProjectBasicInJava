package br.com.project.dao.recoverBdOld;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conection {

	private Connection connection;
	
	private Connection conectOld(){
		String dbURL = "jdbc:mysql://localhost:3306/base_mala_direta?zeroDateTimeBehavior=convertToNull&autoReconnect=true&characterEncoding=UTF-8&characterSetResults=UTF-8";
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

	public Connection getConnectionOld() {
	    if (connection == null)
	    	return conectOld();
	    else
	    	return connection;
	}
	
	public void close() throws SQLException{
		if(!connection.isClosed())
			connection.close();
	}
}
