package br.com.project.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Past;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
	@NamedQuery(name = "PessoaFisica.LitarTodos", query = "from PessoaFisica")
})
public class PessoaFisica extends Pessoa implements Serializable {
	
	private static final long serialVersionUID = -4899342584096517581L;
	
	protected String identidade;
	protected String orgoao;
	protected EnumEstado estadoIdentidade;
	protected Date dataExpedicaoIdentidade;
	protected String profissao;

	public PessoaFisica() {}
	
	public PessoaFisica(Long id) {
		this.setId(id);
	}

	public String getIdentidade() {
		return identidade;
	}

	public void setIdentidade(String identidade) {
		this.identidade = identidade;
	}

	public String getOrgoao() {
		return orgoao;
	}

	public void setOrgoao(String orgoao) {
		this.orgoao = orgoao;
	}

	@Enumerated(EnumType.STRING)
	public EnumEstado getEstadoIdentidade() {
		return estadoIdentidade;
	}

	public void setEstadoIdentidade(EnumEstado estadoIdentidade) {
		this.estadoIdentidade = estadoIdentidade;
	}
	
	@Transient
	public String getOrgoaoUF(){
		if(!getOrgoao().isEmpty() && getEstadoIdentidade() == null){
			return getOrgoao() + "/" + getEstadoIdentidade().toString();
		}
		else if (!getOrgoao().isEmpty()){
			return getOrgoao();
		}
		else if (getEstadoIdentidade() == null){
			return getEstadoIdentidade().toString();
		}
		return "";
	}

	@Past(message = "Data da Expedição não deve ser superior do que a data Atual!")
	@Temporal(TemporalType.DATE)
	public Date getDataExpedicaoIdentidade() {
		return dataExpedicaoIdentidade;
	}

	public void setDataExpedicaoIdentidade(Date dataExpedicaoIdentidade) {
		this.dataExpedicaoIdentidade = dataExpedicaoIdentidade;
	}

	public String getProfissao() {
		return profissao;
	}

	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}
}
