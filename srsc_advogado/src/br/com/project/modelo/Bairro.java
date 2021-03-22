package br.com.project.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({ @NamedQuery(name = "Bairro.LitarTodos", query = "from Bairro") })
public class Bairro implements Serializable {

	private static final long serialVersionUID = 7628283007971860654L;

	private Long id;
	private String nome;
	private Cidade cidade;
	private List<Logradouro> logradouros;
	private List<Cep> ceps;
	private Boolean deletado;
	private Double latitude;
	private Double longitude;
	private Date dataCadastro;
	private Login login;

	public Bairro() {}

	public Bairro(String nome, Cidade cidade, Date dataCadastro, Login login, Boolean deletado) {
		this.nome = nome;
		this.cidade = cidade;
		this.dataCadastro = dataCadastro;
		this.login = login;
		this.deletado = deletado;
	}

	public Bairro(String nome, Cidade cidade, Date dataCadastro, Login login) {
		this.nome = nome;
		this.cidade = cidade;
		this.dataCadastro = dataCadastro;
		this.login = login;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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

	@ManyToOne
	@JoinColumn(name = "cidade")
	public Cidade getCidade() {
		if (cidade == null)
			cidade = new Cidade();
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	@OneToMany(mappedBy = "bairro", targetEntity = Logradouro.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Logradouro> getLogradouros() {
		return logradouros;
	}

	public void setLogradouros(List<Logradouro> logradouros) {
		this.logradouros = logradouros;
	}

	@OneToMany(mappedBy = "bairro", targetEntity = Cep.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Cep> getCeps() {
		return ceps;
	}

	public void setCeps(List<Cep> ceps) {
		this.ceps = ceps;
	}

	public Boolean getDeletado() {
		return deletado;
	}

	public void setDeletado(Boolean deletado) {
		this.deletado = deletado;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	@Temporal(TemporalType.DATE)
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	@ManyToOne
	@JoinColumn(name = "login")
	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bairro other = (Bairro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
