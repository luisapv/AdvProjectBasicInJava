package br.com.project.relatorio.jasperIreport.malaDireta;

import java.io.Serializable;

public class MalaDireta implements Serializable{

	private static final long serialVersionUID = -2735121361881170669L;

	private String nome_destinatario;
	private String endereco_destinatario;
	private String nome_remetente;
	private String endereco_remetente;

	public String getNome_destinatario() {
		return nome_destinatario;
	}

	public void setNome_destinatario(String nome_destinatario) {
		this.nome_destinatario = nome_destinatario;
	}

	public String getEndereco_destinatario() {
		return endereco_destinatario;
	}

	public void setEndereco_destinatario(String endereco_destinatario) {
		this.endereco_destinatario = endereco_destinatario;
	}

	public String getNome_remetente() {
		return nome_remetente;
	}

	public void setNome_remetente(String nome_remetente) {
		this.nome_remetente = nome_remetente;
	}

	public String getEndereco_remetente() {
		return endereco_remetente;
	}

	public void setEndereco_remetente(String endereco_remetente) {
		this.endereco_remetente = endereco_remetente;
	}
	
}
