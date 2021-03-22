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

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@NamedQueries({
	@NamedQuery(name = "Cidade.LitarTodos", query = "from Cidade")
})
public class Cidade implements Serializable {

	private static final long serialVersionUID = -5202728166307680906L;

	private Long id;
	private String nome;
	private EnumEstado estado;
	private Integer ddd;
	private List<Cep> ceps;
	private List<Bairro> bairros;
	private String codigoIbge;
	private String codigoGia;
	private Boolean deletado;
	private Double latitude;
	private Double longitude;
	private Date dataCadastro;
	private Login login;

	public Cidade(){}
	
	public Cidade(String nome, EnumEstado estado) {
		setNome(nome);
		setEstado(estado);
	}
	
	public Cidade(String nome, EnumEstado estado, Date dataCadasro, Login login, Boolean deletado) {
		setNome(nome);
		setEstado(estado);
		setDataCadastro(dataCadasro);
		setLogin(login);
		setDeletado(deletado);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@NotEmpty(message = "O campo nome da Cidade deve ser preenchida!")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@NotNull(message = "Selecione um Estado!")
	@Enumerated(EnumType.STRING)
	public EnumEstado getEstado() {
		return estado;
	}

	public void setEstado(EnumEstado estado) {
		this.estado = estado;
	}

	public Integer getDdd() {
		return ddd;
	}

	public void setDdd(Integer ddd) {
		this.ddd = ddd;
	}

	@OneToMany(mappedBy = "cidade", targetEntity = Cep.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Cep> getCeps() {
		return ceps;
	}

	public void setCeps(List<Cep> ceps) {
		this.ceps = ceps;
	}

	@OneToMany(mappedBy = "cidade", targetEntity = Bairro.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Bairro> getBairros() {
		return bairros;
	}

	public void setBairros(List<Bairro> bairros) {
		this.bairros = bairros;
	}

	public String getCodigoIbge() {
		return codigoIbge;
	}

	public void setCodigoIbge(String codigoIbge) {
		this.codigoIbge = codigoIbge;
	}

	public String getCodigoGia() {
		return codigoGia;
	}

	public void setCodigoGia(String codigoGia) {
		this.codigoGia = codigoGia;
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
		Cidade other = (Cidade) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
