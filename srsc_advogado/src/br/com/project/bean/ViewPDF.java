package br.com.project.bean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.project.control.LoginController;
import br.com.project.modelo.Login;
import br.com.project.modelo.Processo;
import br.com.project.servico.ProcessoServico;
import br.com.project.util.Mensagem;

@Controller(value = "viewPDF")
@Scope(value = "session")
public class ViewPDF implements Serializable {

	private static final long serialVersionUID = 4289244481595575548L;

	private String processoString = null;
	private Processo processo = null;
	private Boolean status = false;
	private String relatorio = null;

	@Autowired
	private ProcessoServico processoServico;

	@Autowired
	private LoginController loginController;

	private Login loginLogado;

	public void iniciarBean() {
		status = true;
		if (processoString != null) {
			Processo pro = new Processo();
			pro.setProcesso(processoString);
			processo = processoServico.obterPorId(pro);
			if (processo.getArquivoEmpty()) {
				File file = new File(processo.getArquivo());
				if (!file.exists()) {
					status = false;
					Mensagem.mensagemAlerta(null, "Warning!", "PDF não encontrado!");
				}
			} else {
				status = false;
				Mensagem.mensagemAlerta(null, "Warning!", "PDF não encontrado!");
			}
		} else {
			status = false;
		}
	}

	public void malaDiretaBean() {
		status = true;
		relatorio = "MalaDireta";
		processoString = null;
	}

	public StreamedContent getContentPdf() {
		if (status) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
//			Path pdfPath = null;
			 String local = null;
			if (processoString != null)
				local = this.processo.getArquivo();
//			 local = getLoginLogado().getLocalSave().concat(this.processo.getArquivo());
			else if (relatorio == "MalaDireta")
				local = "E:/srsc_advogado/arquivos/Mala_Direta.pdf";
//			 local = getLoginLogado().getLocalSave().concat("srsc_advogado\\arquivos\\Mala_Direta.pdf");
			
			Path pdfPath = Paths.get(local);
			StreamedContent p = null;
			try {
				byte[] pdfFile = Files.readAllBytes(pdfPath);// seus bytes
				out.write(pdfFile, 0, pdfFile.length);
				p = new DefaultStreamedContent(new ByteArrayInputStream(pdfFile), "application/pdf");
			} catch (IOException e) {
				e.printStackTrace();
			}

			return p;
		} else {
			return null;
		}
	}

	public void trocarViewPDF() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		String url = request.getQueryString();
		if (url != null) {
			try {
				if (processoString == "mala")
					malaDiretaBean();
				else
					FacesContext.getCurrentInstance().getExternalContext()
							.redirect("/srsc_advogado/showPDF/" + processoString + "/");
			} catch (IOException e) {
				Mensagem.mensagemErro(null, null, "Erro ao redirecionar página!");
			}
		} else {
			iniciarBean();
		}
	}

	public String getIdFile() {
		return java.util.UUID.randomUUID().toString();
	}

	public String getProcessoString() {
		return processoString;
	}

	public void setProcessoString(String processoString) {
		this.processoString = processoString;
	}

	public Login getLoginLogado() {
		setLoginLogado();
		return loginLogado;
	}

	public void setLoginLogado() {
		this.loginLogado = loginController.obterLoginLogado();
	}
}
