package br.com.project.modelo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("deprecation")
@Entity
@NamedQueries({
	@NamedQuery(name = "Login.LitarTodos", query = "from Login")
})
public class Login implements Serializable, UserDetails {

	private static final long serialVersionUID = -8829948426047048227L;

	private Long id;
	private String nome;
	private String senha;
	private String email;
	private String login;
	private Endereco endereco;
	private EnumTema tema;
	private String localSave;
	private boolean ativo;
	private List<Permissao> permissoes;
	private Date dataCadastro;
	private List<PerfilConfiguracao> perfilConfiguracaos;
	private List<PerfilPreConfiguracao> perfilPreConfiguracaos;
	
	// LISTA DE DEPENDENCIA
	private List<Arquivo> arquivos;
	private List<Bairro> bairros;
	private List<Cep> ceps;
	private List<Cidade> cidades;
	private List<Fase> fases;
	private List<Logradouro> logradouros;
	private List<Materia> materias;
	private List<Pessoa> pessoas;

	public Login() {}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "login_permissao", joinColumns = { @JoinColumn(name = "login") }, inverseJoinColumns = {
			@JoinColumn(name = "permissao") })
	public List<Permissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
	}

	@Temporal(TemporalType.DATE)
	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	@OneToOne(targetEntity = Endereco.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "endereco")
	public Endereco getEndereco() {
//		if (endereco == null)
//			endereco = new Endereco();
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	@Enumerated(EnumType.STRING)
	public EnumTema getTema() {
		if(tema == null){
			tema = EnumTema.afternoon;
		}
		return tema;
	}

	public void setTema(EnumTema tema) {
		this.tema = tema;
	}

	public String getLocalSave() {
		return localSave;
	}

	public void setLocalSave(String localSave) {
		this.localSave = localSave;
	}

	@OneToMany(mappedBy = "login", cascade = CascadeType.ALL)
	public List<PerfilConfiguracao> getPerfilConfiguracaos() {
		return perfilConfiguracaos;
	}

	public void setPerfilConfiguracaos(List<PerfilConfiguracao> perfilConfiguracaos) {
		this.perfilConfiguracaos = perfilConfiguracaos;
	}

	@OneToMany(mappedBy = "login", cascade = CascadeType.ALL)
	public List<PerfilPreConfiguracao> getPerfilPreConfiguracaos() {
		return perfilPreConfiguracaos;
	}

	public void setPerfilPreConfiguracaos(List<PerfilPreConfiguracao> perfilPreConfiguracaos) {
		this.perfilPreConfiguracaos = perfilPreConfiguracaos;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@Transient
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.add(new GrantedAuthorityImpl("ROLE_USER"));
		return authorities;
	}

	@Transient
	public String getPassword() {
		return senha;
	}

	@Transient
	public String getUsername() {
		return login;
	}

	@Transient
	public boolean isAccountNonExpired() {
		return true;
	}

	@Transient
	public boolean isAccountNonLocked() {
		return true;
	}

	@Transient
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Transient
	public boolean isEnabled() {
		return ativo;
	}
	
	@OneToMany(mappedBy = "login", cascade = CascadeType.ALL)
	public List<Arquivo> getArquivos() {
		return arquivos;
	}

	public void setArquivos(List<Arquivo> arquivos) {
		this.arquivos = arquivos;
	}

	@OneToMany(mappedBy = "login", cascade = CascadeType.ALL)
	public List<Bairro> getBairros() {
		return bairros;
	}

	public void setBairros(List<Bairro> bairros) {
		this.bairros = bairros;
	}

	@OneToMany(mappedBy = "login", cascade = CascadeType.ALL)
	public List<Cep> getCeps() {
		return ceps;
	}

	public void setCeps(List<Cep> ceps) {
		this.ceps = ceps;
	}

	@OneToMany(mappedBy = "login", cascade = CascadeType.ALL)
	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	@OneToMany(mappedBy = "login", cascade = CascadeType.ALL)
	public List<Fase> getFases() {
		return fases;
	}

	public void setFases(List<Fase> fases) {
		this.fases = fases;
	}

	@OneToMany(mappedBy = "login", cascade = CascadeType.ALL)
	public List<Logradouro> getLogradouros() {
		return logradouros;
	}

	public void setLogradouros(List<Logradouro> logradouros) {
		this.logradouros = logradouros;
	}

	@OneToMany(mappedBy = "login", cascade = CascadeType.ALL)
	public List<Materia> getMaterias() {
		return materias;
	}

	public void setMaterias(List<Materia> materias) {
		this.materias = materias;
	}

	@OneToMany(mappedBy = "login", cascade = CascadeType.ALL)
	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	@SuppressWarnings("null")
	@Transient
	public List<Boolean> columnVisibleDataTable(String pagina, Integer quantidade){
//		for (PerfilPreConfiguracao perfilPreConfiguracao : perfilPreConfiguracaos) {
//			if(perfilPreConfiguracao.getPagina().equals(pagina)){
//				if(perfilPreConfiguracao.getColumnVisibleDataTable().isEmpty() || perfilPreConfiguracao.getColumnVisibleDataTable() == null)
//				return perfilPreConfiguracao.getColumnVisibleDataTable();
//			}
//		}
		List<Boolean> retorno = null;
		for(Integer x=0; x<quantidade;x++){
			retorno.add(true);
		}
		return retorno;
	}
}
