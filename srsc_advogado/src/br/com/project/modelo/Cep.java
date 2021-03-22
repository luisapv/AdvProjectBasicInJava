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
import javax.validation.constraints.NotNull;

@Entity
@NamedQueries({
	@NamedQuery(name = "Cep.LitarTodos", query = "from Cep")
})
public class Cep implements Serializable {

	private static final long serialVersionUID = 626342861465572362L;

	private Long id;
	private String cep;
	private Logradouro logradouro;
	private Bairro bairro;
	private Cidade cidade;
	private EnumEstado estado;
	private String complemento;
	private String unidade;
	private List<Endereco> enderecos;
	private String ibge;
	private String gia;
	private Boolean cepCorreto;
	private Double latitude;
	private Double longitude;
	private Date dataCadastro;
	private Login login;
	
	public Cep() {}

	public Cep(String cep, EnumEstado estado, Cidade cidade, Bairro bairro, Logradouro logradouro, Date dataCadastro,
			Login login) {
		this.cep = cep;
		this.estado = estado;
		this.cidade = cidade;
		this.bairro = bairro;
		this.logradouro = logradouro;
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

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="logradouro")
	@NotNull(message = "Selecione um Logradouro!")
	public Logradouro getLogradouro() {
		if (logradouro == null)
			logradouro = new Logradouro();
		return logradouro;
	}

	public void setLogradouro(Logradouro logradouro) {
		this.logradouro = logradouro;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="bairro")
	@NotNull(message = "Selecione um Bairro!")
	public Bairro getBairro() {
		if (bairro == null)
			bairro = new Bairro();
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="cidade")
	@NotNull(message = "Selecione uma Cidade!")
	public Cidade getCidade() {
		if(cidade == null)
			cidade = new Cidade();
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	@NotNull(message = "Selecione um Estado!")
	@Enumerated(EnumType.STRING)
	public EnumEstado getEstado() {
		return estado;
	}

	public void setEstado(EnumEstado estado) {
		this.estado = estado;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	@OneToMany(mappedBy = "cep", targetEntity = Endereco.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public String getIbge() {
		return ibge;
	}

	public void setIbge(String ibge) {
		this.ibge = ibge;
	}

	public String getGia() {
		return gia;
	}

	public void setGia(String gia) {
		this.gia = gia;
	}

	public Boolean getCepCorreto() {
		return cepCorreto;
	}

	public void setCepCorreto(Boolean cepCorreto) {
		this.cepCorreto = cepCorreto;
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

	@Temporal(TemporalType.DATE)
	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	@ManyToOne
	@JoinColumn(name="login")
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
		Cep other = (Cep) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
