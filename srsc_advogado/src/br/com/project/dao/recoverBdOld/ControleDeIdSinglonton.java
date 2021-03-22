package br.com.project.dao.recoverBdOld;

public class ControleDeIdSinglonton {
	private static ControleDeID instacia;
	
	public ControleDeID getInstacia(){
		if (instacia == null)
			instacia = new ControleDeID();
		return instacia;
	}
}
