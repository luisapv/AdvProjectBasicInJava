package br.com.project.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@NamedQueries({ @NamedQuery(name = "Endereco.LitarTodos", query = "from Endereco") })
public class Endereco implements Serializable {

	private static final long serialVersionUID = 3490457432982967370L;

	private Long id;
	private Pessoa pessoa;
	private String numero;
	private String complemento;
	private String referencia;
	private Cep cep;
	private Float latitude;
	private Float longitude;
	private Date dataCadastro;
	private Login login;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Cep.class)
	@JoinColumn(name = "cep", nullable = false)
	public Cep getCep() {
		if (this.cep == null) {
			cep = new Cep();
		}
		return cep;
	}

	public void setCep(Cep cep) {
		this.cep = cep;
	}

	@Lob
	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pessoa")
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
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
	public String getEnderecoCompleto(){
		StringBuilder enderecoCompleto = new StringBuilder();
		if (this.getCep() != null) {
			enderecoCompleto.append(this.getCep().getLogradouro().getLogradouroCompleto());
			enderecoCompleto.append(", ");
			if (this.getNumero() != "" && this.getNumero() != null && !this.getNumero().isEmpty()) {
				enderecoCompleto.append(this.getNumero());
				if (this.getComplemento() != ""
						&& this.getComplemento() != null && !this.getComplemento().isEmpty()) {
					enderecoCompleto.append(" - ");
					enderecoCompleto.append(this.getComplemento());
				}
				enderecoCompleto.append(", ");
			} else if (this.getComplemento() != ""
					&& this.getComplemento() != null && !this.getComplemento().isEmpty()) {
				enderecoCompleto.append(this.getComplemento());
				enderecoCompleto.append(", ");
			}
			enderecoCompleto.append(this.getCep().getBairro().getNome());
			enderecoCompleto.append(", ");
			enderecoCompleto.append(this.getCep().getCidade().getNome());
			enderecoCompleto.append("/");
			enderecoCompleto.append(this.getCep().getEstado());
			enderecoCompleto.append(" - ");
			enderecoCompleto.append(this.getCep().getCep());
		}
		if (!enderecoCompleto.toString().isEmpty())
			return enderecoCompleto.toString();
		else
			return null;
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
		Endereco other = (Endereco) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
