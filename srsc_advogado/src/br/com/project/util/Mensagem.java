package br.com.project.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Mensagem {

	public static void mensagemInformacao(String id, String mensagem, String titulo){
		FacesContext.getCurrentInstance().addMessage(id, 
				new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, mensagem));
	}
	
	public static void mensagemAlerta(String id, String mensagem, String titulo){
		FacesContext.getCurrentInstance().addMessage(id, 
				new FacesMessage(FacesMessage.SEVERITY_WARN, titulo, mensagem));
	}
	
	public static void mensagemErro(String id, String mensagem, String titulo){
		FacesContext.getCurrentInstance().addMessage(id, 
				new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, mensagem));
	}
	
	public void mensagemFatal(String id, String mensagem, String titulo) {
        FacesContext.getCurrentInstance().addMessage(id, new FacesMessage(FacesMessage.SEVERITY_FATAL, titulo, mensagem));
    }
}
