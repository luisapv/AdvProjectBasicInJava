package br.com.project.modelo;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
	@NamedQuery(name = "PessoaJuridica.LitarTodos", query = "from PessoaJuridica")
})
public class PessoaJuridica extends Pessoa {

	private static final long serialVersionUID = 959949559776113856L;

	private String pessoaResponsavel;

	public String getPessoaResponsavel() {
		return pessoaResponsavel;
	}

	public void setPessoaResponsavel(String pessoaResponsavel) {
		this.pessoaResponsavel = pessoaResponsavel;
	}
}
