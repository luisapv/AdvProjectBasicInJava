package br.com.project.dao.recoverBdOld;

public class ConectionSinglonton {

	private Conection instance;
	
	public Conection getInstance(){
		if (instance == null) {
			instance = new Conection();
		}
		return instance;
	}
}
