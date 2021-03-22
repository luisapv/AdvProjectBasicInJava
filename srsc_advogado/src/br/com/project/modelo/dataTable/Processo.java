package br.com.project.modelo.dataTable;

import java.io.Serializable;
import java.util.Date;

public class Processo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String processo;
	private String arquivo;
	private boolean arquivoEmpty;
	private Date dataArquivo;
	private String juizSenteca;
	private Integer trtTurma;
	private String trtRelator;
	private Integer tstTurma;
	private String tstRelator;
	private String autores;
	private String advAutor;
	private String reus;
	private String advReu;
	private String fases;
	private String materias;
	private String cadastradoPor;
	private Date dataCadastro;

	public String getProcesso() {
		return processo;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

	public boolean isArquivoEmpty() {
		return arquivoEmpty;
	}

	public void setArquivoEmpty(boolean arquivoEmpty) {
		this.arquivoEmpty = arquivoEmpty;
	}

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

	public String getAutores() {
		return autores;
	}

	public void setAutores(String autores) {
		this.autores = autores;
	}

	public String getAdvAutor() {
		return advAutor;
	}

	public void setAdvAutor(String advAutor) {
		this.advAutor = advAutor;
	}

	public String getReus() {
		return reus;
	}

	public void setReus(String reus) {
		this.reus = reus;
	}

	public String getAdvReu() {
		return advReu;
	}

	public void setAdvReu(String advReu) {
		this.advReu = advReu;
	}

	public String getFases() {
		return fases;
	}

	public void setFases(String fases) {
		this.fases = fases;
	}

	public String getMaterias() {
		return materias;
	}

	public void setMaterias(String materias) {
		this.materias = materias;
	}

	public String getCadastradoPor() {
		return cadastradoPor;
	}

	public void setCadastradoPor(String cadastradoPor) {
		this.cadastradoPor = cadastradoPor;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
}
