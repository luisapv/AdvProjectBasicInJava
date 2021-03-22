package br.com.project.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import javax.persistence.Transient;

@Entity
@NamedQueries({ @NamedQuery(name = "Logradouro.LitarTodos", query = "from Logradouro") })
public class Logradouro implements Serializable {

	private static final long serialVersionUID = 6911273641173773145L;

	private Long id;
	private EnumTipoLogradouro tipoLogradouro;
	private String nome;
	private String complemento;
	private Bairro bairro;
	private List<Cep> ceps;
	private Date dataCadastro;
	private Login login;

	public Logradouro() {}

	public Logradouro(String nome, Bairro bairro, Date dataCadastro, Login login) {
		this.nome = nome;
		this.bairro = bairro;
		this.dataCadastro = dataCadastro;
		this.login = login;
	}

	public Logradouro(EnumTipoLogradouro tipoLogradouro, String nome, Bairro bairro, Date dataCadastro, Login login) {
		this.tipoLogradouro = tipoLogradouro;
		this.nome = nome;
		this.bairro = bairro;
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

	@Enumerated(EnumType.STRING)
	public EnumTipoLogradouro getTipoLogradouro() {
		return tipoLogradouro;
	}

	public void setTipoLogradouro(EnumTipoLogradouro tipoLogradouro) {
		this.tipoLogradouro = tipoLogradouro;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	@ManyToOne
	@JoinColumn(name = "bairro")
	public Bairro getBairro() {
		if (bairro == null) {
			bairro = new Bairro();
		}
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	@OneToMany(mappedBy = "logradouro", targetEntity = Cep.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Cep> getCeps() {
		return ceps;
	}

	public void setCeps(List<Cep> ceps) {
		this.ceps = ceps;
	}

	@Temporal(TemporalType.DATE)
	public Date getDataCadastro() {
		return dataCadastro;
	}

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
	
	@Transient
	public String getLogradouroCompleto(){
		if (this.getTipoLogradouro() != null)
			return this.getTipoLogradouro().getLabel() + " " + this.getNome();
		else
			return this.getNome();
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
		Logradouro other = (Logradouro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
