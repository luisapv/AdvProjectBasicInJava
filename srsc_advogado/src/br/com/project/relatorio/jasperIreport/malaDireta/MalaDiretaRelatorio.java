package br.com.project.relatorio.jasperIreport.malaDireta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import br.com.project.modelo.Login;
import br.com.project.modelo.Pessoa;
import br.com.project.modelo.dataTable.Endereco;
import br.com.project.modelo.dataTable.PessoaFisica;
import br.com.project.modelo.dataTable.PessoaJuridica;
import br.com.project.util.Mensagem;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class MalaDiretaRelatorio {

	// Caminho base
	private String path;
	// Caminho para o package onde est√£o armazenados os relatorios Jarper
	private String pathToReportPackage;
	// Caminho para salvar arquivo
	private String fileSave;

	// Recupera os caminhos para que a classe possa encontrar os relat√≥rios
	public MalaDiretaRelatorio() {
		this.path = this.getClass().getClassLoader().getResource("").getPath();
//		File fileJasper = new File("E://srsc_advogado//arquivos//malaDireta.jrxml");
		this.pathToReportPackage = FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\WEB-INF\\classes\\br\\com\\project\\relatorio\\jasperIreport\\malaDireta\\malaDireta.jrxml");

		System.out.println(this.pathToReportPackage);
		this.fileSave = "E:/srsc_advogado/arquivos/Mala_Direta.pdf";
		System.out.println(path);
	}

	// Imprime uma lista de PessoaFisica
	public void imprimirPessoaFisica(List<PessoaFisica> pessoas, Login login) throws Exception {
		List<MalaDireta> malaDireta = montarListaPessoaFisica(pessoas, login);

//		this.pathToReportPackage = "E://Usuarios//Luis Alfredo//Desktop//Estudo//Java/Eclipse//srsc_advogado//src//br//com//project//relatorio//jasperIreport//malaDireta//";

		JasperReport report = JasperCompileManager.compileReport(this.pathToReportPackage);

		JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(malaDireta));

		JasperExportManager.exportReportToPdfFile(print, fileSave);

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/srsc_advogado/showMalaDireta/");
		} catch (IOException e) {
			Mensagem.mensagemErro(null, null, "Erro ao redirecionar p·gina!");
		} finally {
			Mensagem.mensagemErro(null, null, "Erro ao redirecionar p·gina!");
		}

		// JasperViewer jv = new JasperViewer(print);

		// jv.setVisible(true);

	}
	
	// Gera uma lista de PessoaFisica
	private List<MalaDireta> montarListaPessoaFisica(List<PessoaFisica> pessoas, Login login) {
		List<MalaDireta> malaDireta = new ArrayList<MalaDireta>();
		MalaDireta malaD = null;
		for (PessoaFisica pessoa : pessoas) {
			if (!pessoa.getEnderecoCompleto().isEmpty() && pessoa.getEnderecoCompleto() != null) {
				malaD = new MalaDireta();
				malaD.setNome_destinatario(pessoa.getNome());
				malaD.setEndereco_destinatario(pessoa.getEnderecoCompleto());
				malaD.setNome_remetente(login.getNome());
				malaD.setEndereco_remetente(login.getEndereco().getEnderecoCompleto());
				malaDireta.add(malaD);
			}
		}
		return malaDireta;
	}

	// Imprime uma lista de PessoaJuridica
	public void imprimirPessoaJuridica(List<PessoaJuridica> pessoas, Login login) throws Exception {
		List<MalaDireta> malaDireta = montarListaPessoaJuridica(pessoas, login);

//		this.pathToReportPackage = "E://Usuarios//Luis Alfredo//Desktop//Estudo//Java/Eclipse//srsc_advogado//src//br//com//project//relatorio//jasperIreport//malaDireta//";

		JasperReport report = JasperCompileManager.compileReport(this.pathToReportPackage);

		JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(malaDireta));

		JasperExportManager.exportReportToPdfFile(print, fileSave);

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/srsc_advogado/showMalaDireta/");
		} catch (IOException e) {
			Mensagem.mensagemErro(null, null, "Erro ao redirecionar p√°gina!");
		} finally {
			Mensagem.mensagemErro(null, null, "Erro ao redirecionar p√°gina!");
		}

		// JasperViewer jv = new JasperViewer(print);

		// jv.setVisible(true);

	}
	
	// Gera uma lista de PessoaJuridica
	private List<MalaDireta> montarListaPessoaJuridica(List<PessoaJuridica> pessoas, Login login) {
		List<MalaDireta> malaDireta = new ArrayList<MalaDireta>();
		MalaDireta malaD = null;
		for (PessoaJuridica pessoa : pessoas) {
			if (!pessoa.getEnderecoCompleto().isEmpty() && pessoa.getEnderecoCompleto() != null) {
				malaD = new MalaDireta();
				malaD.setNome_destinatario(pessoa.getNome());
				malaD.setEndereco_destinatario(pessoa.getEnderecoCompleto());
				malaD.setNome_remetente(login.getNome());
				malaD.setEndereco_remetente(login.getEndereco().getEnderecoCompleto());
				malaDireta.add(malaD);
			}
		}
		return malaDireta;
	}

	// Imprime uma lista de Pessoa
	public void imprimirPessoa(List<Pessoa> pessoas, Login login) throws Exception {
		List<MalaDireta> malaDireta = montarListaPessoa(pessoas, login);

//		this.pathToReportPackage = "E://Usuarios//Luis Alfredo//Desktop//Estudo//Java/Eclipse//srsc_advogado//src//br//com//project//relatorio//jasperIreport//malaDireta//";

		JasperReport report = JasperCompileManager.compileReport(this.pathToReportPackage);

		JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(malaDireta));

		JasperExportManager.exportReportToPdfFile(print, fileSave);

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/srsc_advogado/showMalaDireta/");
		} catch (IOException e) {
			Mensagem.mensagemErro(null, null, "Erro ao redirecionar p·gina!");
		} finally {
			Mensagem.mensagemErro(null, null, "Erro ao redirecionar p·gina!");
		}

		// JasperViewer jv = new JasperViewer(print);

		// jv.setVisible(true);

	}
	
	// Gera uma lista de Pessoa
	private List<MalaDireta> montarListaPessoa(List<Pessoa> pessoas, Login login) {
		List<MalaDireta> malaDireta = new ArrayList<MalaDireta>();
		MalaDireta malaD = null;
		for (Pessoa pessoa : pessoas) {
			if (!pessoa.getEnderecos().isEmpty() && pessoa.getEnderecos() != null) {
				malaD = new MalaDireta();
				malaD.setNome_destinatario(pessoa.getNome());
				malaD.setEndereco_destinatario(pessoa.getEnderecoCompleto());
				malaD.setNome_remetente(login.getNome());
				malaD.setEndereco_remetente(login.getEndereco().getEnderecoCompleto());
				malaDireta.add(malaD);
			}
		}
		return malaDireta;
	}

	// Imprime uma lista de Endereco
	public void imprimirEndereco(List<Endereco> enderecos, Login login) throws Exception {
		List<MalaDireta> malaDireta = montarListaEndereco(enderecos, login);

//		this.pathToReportPackage = "E://Usuarios//Luis Alfredo//Desktop//Estudo//Java/Eclipse//srsc_advogado//src//br//com//project//relatorio//jasperIreport//malaDireta//";

		JasperReport report = JasperCompileManager.compileReport(this.pathToReportPackage);

		JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(malaDireta));

		JasperExportManager.exportReportToPdfFile(print, fileSave);

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/srsc_advogado/showMalaDireta/");
		} catch (IOException e) {
			Mensagem.mensagemErro(null, null, "Erro ao redirecionar p·gina!");
		} finally {
			Mensagem.mensagemErro(null, null, "Erro ao redirecionar p·gina!");
		}

		// JasperViewer jv = new JasperViewer(print);

		// jv.setVisible(true);

	}
	
	// Gera uma lista de Endereco
	private List<MalaDireta> montarListaEndereco(List<Endereco> enderecos, Login login) {
		List<MalaDireta> malaDireta = new ArrayList<MalaDireta>();
		MalaDireta malaD = null;
		for (Endereco endereco : enderecos) {
			if (endereco.getPessoa() != null) {
				malaD = new MalaDireta();
				malaD.setNome_destinatario(endereco.getPessoa());
				malaD.setEndereco_destinatario(endereco.getEnderecoCompleto());
				malaD.setNome_remetente(login.getNome());
				malaD.setEndereco_remetente(login.getEndereco().getEnderecoCompleto());
				malaDireta.add(malaD);
			}
		}
		return malaDireta;
	}

	public String getPath() {
		return this.path;
	}

	public String getPathToReportPackage() {
		return this.pathToReportPackage;
	}

	public String getFileSave() {
		return this.fileSave;
	}
}
