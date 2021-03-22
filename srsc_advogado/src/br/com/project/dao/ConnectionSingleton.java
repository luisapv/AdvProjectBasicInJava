package br.com.project.dao;

public class ConnectionSingleton {
	
	private Conection conection;
	
	public Conection getConection(){
		if (conection == null)
			conection = new Conection();
		return conection;
	}
}
