package br.com.project.modelo;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@NamedQueries({ @NamedQuery(name = "Processo.LitarTodos", query = "from Processo") })
public class Processo implements Serializable {

	private static final long serialVersionUID = 2296045797045947736L;

	private String processo;
	private String descricao;
	private String arquivo;
	private Date dataArquivo;
	private String juizSenteca;
	private Integer trtTurma;
	private String trtRelator;
	private Integer tstTurma;
	private String tstRelator;
	private List<Advogado> advAutor;
	private List<Pessoa> autores;
	private List<Advogado> advReu;
	private List<Pessoa> reus;
	private List<Fase> fases;
	private List<Materia> materias;
	private String status;
	private Date dataCadastro;
	private Login login;

	@Id
	public String getProcesso() {
		return processo;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}

	@Lob
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

	@Temporal(TemporalType.DATE)
	public Date getDataArquivo() {
		return dataArquivo;
	}

	public void setDataArquivo(Date dataArquivo) {
		this.dataArquivo = dataArquivo;
	}

	public String getJuizSenteca() {
		return juizSenteca;
	}

	public void setJuizSenteca(String juizSenteca) {
		this.juizSenteca = juizSenteca;
	}

	public Integer getTrtTurma() {
		return trtTurma;
	}

	public void setTrtTurma(Integer trtTurma) {
		this.trtTurma = trtTurma;
	}

	public String getTrtRelator() {
		return trtRelator;
	}

	public void setTrtRelator(String trtRelator) {
		this.trtRelator = trtRelator;
	}

	public Integer getTstTurma() {
		return tstTurma;
	}

	public void setTstTurma(Integer tstTurma) {
		this.tstTurma = tstTurma;
	}

	public String getTstRelator() {
		return tstRelator;
	}

	public void setTstRelator(String tstRelator) {
		this.tstRelator = tstRelator;
	}

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "processo_advogado_autor", joinColumns = {
			@JoinColumn(name = "processo") }, inverseJoinColumns = { @JoinColumn(name = "advogado") })
	public List<Advogado> getAdvAutor() {
		return advAutor;
	}

	public void setAdvAutor(List<Advogado> advAutor) {
		this.advAutor = advAutor;
	}

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "processo_autor", joinColumns = { @JoinColumn(name = "processo") }, inverseJoinColumns = {
			@JoinColumn(name = "pessoa") })
	public List<Pessoa> getAutores() {
		return autores;
	}

	public void setAutores(List<Pessoa> autores) {
		this.autores = autores;
	}

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "processo_advogado_reu", joinColumns = { @JoinColumn(name = "processo") }, inverseJoinColumns = {
			@JoinColumn(name = "advogado") })
	public List<Advogado> getAdvReu() {
		return advReu;
	}

	public void setAdvReu(List<Advogado> advReu) {
		this.advReu = advReu;
	}

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "processo_reu", joinColumns = { @JoinColumn(name = "processo") }, inverseJoinColumns = {
			@JoinColumn(name = "pessoa") })
	public List<Pessoa> getReus() {
		return reus;
	}

	public void setReus(List<Pessoa> reus) {
		this.reus = reus;
	}

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "processo_fase", joinColumns = { @JoinColumn(name = "processo") }, inverseJoinColumns = {
			@JoinColumn(name = "fase") })
	public List<Fase> getFases() {
		return fases;
	}

	public void setFases(List<Fase> fase) {
		this.fases = fase;
	}

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "processo_materia", joinColumns = { @JoinColumn(name = "processo") }, inverseJoinColumns = {
			@JoinColumn(name = "materia") })
	public List<Materia> getMaterias() {
		return materias;
	}

	public void setMaterias(List<Materia> materias) {
		this.materias = materias;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
	public Integer sizeAdvAutores() {
		return this.advAutor.size();
	}

	@Transient
	public Integer sizeAutores() {
		return this.autores.size();
	}

	@Transient
	public Integer sizeAdvReus() {
		return this.advReu.size();
	}

	@Transient
	public Integer sizeReus() {
		return this.reus.size();
	}

	@Transient
	public Integer sizeFases() {
		return this.fases.size();
	}

	@Transient
	public Integer sizeMaterias() {
		return this.materias.size();
	}

	@Transient
	public Boolean getArquivoEmpty() {
		if (this.getArquivo() == null) {
			return false;
		} else {
			File file = new File(this.arquivo);
			return file.exists();
		}
	}

//	@Transient
//	public String getArquivoEmptyString() {
//		if (this.getArquivoEmpty())
//			return "1";
//		else
//			return "0";
//	}
//
//	@Transient
//	public String linkPDF() {
//		return "/visualizarPDF/" + this.getProcesso() + "/";
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((processo == null) ? 0 : processo.hashCode());
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
		Processo other = (Processo) obj;
		if (processo == null) {
			if (other.processo != null)
				return false;
		} else if (!processo.equals(other.processo))
			return false;
		return true;
	}
}
