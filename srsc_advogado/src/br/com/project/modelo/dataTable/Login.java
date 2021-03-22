package br.com.project.modelo.dataTable;

import java.util.Date;

public class Login {

	private Long id;
	private String nome;
	private String login;
	private String email;
	private String tema;
	private String localSave;
	private String enderecoCompleto;
	private Boolean ativo;
	private Date dataCadastro;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTema() {
		return tema;
	}
	public void setTema(String tema) {
		this.tema = tema;
	}
	public String getLocalSave() {
		return localSave;
	}
	public void setLocalSave(String localSave) {
		this.localSave = localSave;
	}
	public String getEnderecoCompleto() {
		return enderecoCompleto;
	}
	public void setEnderecoCompleto(String enderecoCompleto) {
		this.enderecoCompleto = enderecoCompleto;
	}
	public Boolean getAtivo() {
		return ativo;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
}
