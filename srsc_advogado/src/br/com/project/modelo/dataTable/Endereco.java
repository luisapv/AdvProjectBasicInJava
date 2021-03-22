package br.com.project.modelo.dataTable;

public class Endereco {

	private Long id;
	private String pessoa;
	private String documento;
	private String numero;
	private String complemento;
	private String cep;
	private String uf;
	private String cidade;
	private String bairro;
	private String logradouro;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPessoa() {
		return this.pessoa;
	}

	public void setPessoa(String pessoa) {
		this.pessoa = pessoa;
	}

	public String getDocumento() {
		return this.documento;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return this.complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getCep() {
		return this.cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getUf() {
		return this.uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCidade() {
		return this.cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getBairro() {
		return this.bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLogradouro() {
		return this.logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getEnderecoCompleto() {
		String enderecoCompleto = logradouro;
		if ((numero != "" && numero != null) && (complemento != "" && complemento != null)) {
			enderecoCompleto += ", " + complemento + " - " + numero;
		} else if (numero != "" && numero != null) {
			enderecoCompleto += ", " + numero;
		} else if (complemento != "" && complemento != null) {
			enderecoCompleto += ", " + complemento;
		}
		enderecoCompleto += ", " + cidade + "\\" + uf + " - " + cep;
		return enderecoCompleto;
	}
}
