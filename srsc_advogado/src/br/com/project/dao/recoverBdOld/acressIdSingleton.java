package br.com.project.dao.recoverBdOld;

public class acressIdSingleton {

	private AcressId instance;
	
	private acressIdSingleton() {}
	
	public AcressId getInstance(){
		if (instance == null)
			instance = new AcressId();
		return instance;
	}
}
