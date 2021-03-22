package br.com.project.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
	@NamedQuery(name = "PerfilConfiguracao.LitarTodos", query = "from PerfilConfiguracao")
})
public class PerfilConfiguracao implements Serializable {

	private static final long serialVersionUID = 4619732362105654087L;

	private Long id;
	private Login login;
	private Boolean processosVisiveisTodos;
	private List<Login> loginsVizualiazamTodosProcessos;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="login")
	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	@ElementCollection
	@CollectionTable(name = "PerfilConfiguacao_LoginsVizualiazamTodosProcessos", joinColumns = @JoinColumn(name = "user_config"))
    @Column(name = "Login_Vizualizam")
	public List<Login> getLoginsVizualiazamTodosProcessos() {
		return loginsVizualiazamTodosProcessos;
	}
	
	public void setLoginsVizualiazamTodosProcessos(List<Login> loginsVizualiazamTodosProcessos) {
		this.loginsVizualiazamTodosProcessos = loginsVizualiazamTodosProcessos;
	}
	
	public Boolean getProcessosVisiveisTodos() {
		return processosVisiveisTodos;
	}
	
	public void setProcessosVisiveisTodos(Boolean processosVisiveisTodos) {
		this.processosVisiveisTodos = processosVisiveisTodos;
	}
}
