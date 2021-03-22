package br.com.project.modelo.dataTable;

public class Logradouro {

	private Long id;
	private String nome;
	private String bairroCidadeUf;

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

	public String getBairroCidadeUf() {
		return bairroCidadeUf;
	}

	public void setBairroCidadeUf(String bairroCidadeUf) {
		this.bairroCidadeUf = bairroCidadeUf;
	}
}
