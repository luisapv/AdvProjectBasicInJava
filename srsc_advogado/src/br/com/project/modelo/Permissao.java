package br.com.project.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
	@NamedQuery(name = "Permissao.LitarTodos", query = "from Permissao")
})
public class Permissao implements Serializable {

	private static final long serialVersionUID = 7270315438322147373L;

	private Long id;
	private String nome;
	private String descricao;
	private List<Login> logins;
	private List<EnumTipoPermissao> tipoPermissao;

	public Permissao() {}
	
	public Permissao(String nome, String descricao, List<EnumTipoPermissao> tipoPermissao){
		this.nome = nome;
		this.descricao = descricao;
		this.tipoPermissao = tipoPermissao;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@ManyToMany(mappedBy = "permissoes")
	public List<Login> getLogins() {
		return logins;
	}
	
	public void setLogins(List<Login> logins) {
		this.logins = logins;
	}
	
	@ElementCollection(targetClass = EnumTipoPermissao.class)
	@CollectionTable(
	        name = "tiposPermissoes", 
	        joinColumns = @JoinColumn(name = "permissao")
	)
	@Column(name = "tipoPermissao")
    @Enumerated(EnumType.STRING)
	public List<EnumTipoPermissao> getTipoPermissao() {
		return tipoPermissao;
	}

	public void setTipoPermissao(List<EnumTipoPermissao> tipoPermissao) {
		this.tipoPermissao = tipoPermissao;
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
		Permissao other = (Permissao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
