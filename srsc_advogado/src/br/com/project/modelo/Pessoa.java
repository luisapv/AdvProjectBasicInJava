package br.com.project.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({ @NamedQuery(name = "Pessoa.LitarTodos", query = "from Pessoa") })
public abstract class Pessoa implements Serializable {

	private static final long serialVersionUID = 8121633550497221803L;

	private Long id;
	private String nome;
	private String apelido;
	private String documento;
	private Date dataNascimento;
	private String email;
	private String observacao;
	private List<Endereco> enderecos;
	private Boolean deletado;
	private Date dataDeletado;
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

	@NotEmpty(message = "O campo do nome deve ser preenchida!")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	@Temporal(TemporalType.DATE)
	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Lob
	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@OneToMany(mappedBy = "pessoa", fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Endereco.class)
	public List<Endereco> getEnderecos() {
		 if (this.enderecos == null){
			 this.enderecos = new ArrayList<Endereco>();
			 this.enderecos.add(new Endereco());
		 }
		return this.enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Boolean getDeletado() {
		return deletado;
	}

	public void setDeletado(Boolean deletado) {
		this.deletado = deletado;
	}

	@Temporal(TemporalType.DATE)
	public Date getDataDeletado() {
		return dataDeletado;
	}

	public void setDataDeletado(Date dataDeletado) {
		this.dataDeletado = dataDeletado;
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
	public String getEnderecoCompleto() {
		if (this.getEnderecos().size() > 0)
			return this.getEnderecos().get(0).getEnderecoCompleto();
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
		Pessoa other = (Pessoa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
