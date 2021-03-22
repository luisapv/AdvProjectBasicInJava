package br.com.project.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
	@NamedQuery(name = "Advogado.LitarTodos", query = "from Advogado")
})
public class Advogado extends PessoaFisica implements Serializable {

	private static final long serialVersionUID = 6812578223945394152L;
	
	public Advogado() {
		this.setProfissao("ADVOGADO");
		this.setOrgoao("OAB");
	}
	
	@Transient
	@NotEmpty(message = "O campo da OAB deve ser preenchida!")
	public String getOAB() {
		return super.getIdentidade();
	}

	@Transient
	public void setOAB(String oab) {
		super.setIdentidade(oab);
	}
	
	@Transient
	@NotNull(message = "Deve selecionar o estado da OAB!")
	public EnumEstado getEstadoOAB() {
		return super.getEstadoIdentidade();
	}

	@Transient
	public void setEstadoOAB(EnumEstado estado) {
		super.setEstadoIdentidade(estado);
	}
	
}
