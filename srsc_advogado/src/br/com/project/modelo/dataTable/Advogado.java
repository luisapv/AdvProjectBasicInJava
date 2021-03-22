package br.com.project.modelo.dataTable;

import java.util.Date;

public class Advogado {

	private Long id;
	private String nome;
	private String oab;
	private Date dataExpedicaoAob;
	private String cadastradoPor;
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

	public String getOab() {
		return oab;
	}

	public void setOab(String oab) {
		this.oab = oab;
	}

	public Date getDataExpedicaoAob() {
		return dataExpedicaoAob;
	}

	public void setDataExpedicaoAob(Date dataExpedicaoAob) {
		this.dataExpedicaoAob = dataExpedicaoAob;
	}

	public String getCadastradoPor() {
		return cadastradoPor;
	}

	public void setCadastradoPor(String cadastradoPor) {
		this.cadastradoPor = cadastradoPor;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
}
