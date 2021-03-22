package br.com.project.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.project.control.LoginController;
import br.com.project.modelo.Advogado;
import br.com.project.modelo.EnumPaginas;
import br.com.project.modelo.Fase;
import br.com.project.modelo.Login;
import br.com.project.modelo.Materia;
import br.com.project.modelo.PerfilPreConfiguracao;
import br.com.project.modelo.Pessoa;
import br.com.project.modelo.PessoaFisica;
import br.com.project.modelo.PessoaJuridica;
import br.com.project.modelo.Processo;
import br.com.project.servico.AdvogadoServico;
import br.com.project.servico.FaseServico;
import br.com.project.servico.MateriaServico;
import br.com.project.servico.PessoaFisicaServico;
import br.com.project.servico.PessoaJuridicaServico;
import br.com.project.servico.ProcessoServico;
import br.com.project.util.Mensagem;

@Controller(value = "processoBean")
@Scope(value = "session")
public class ProcessoBean implements Serializable {

	private static final long serialVersionUID = 2697264978280707957L;

	private Processo processo;

	// private List<Processo> processos = new ArrayList<Processo>();
	private List<br.com.project.modelo.dataTable.Processo> lazyProcessoDataTable;

	@Autowired
	private ProcessoServico processoServico;
	@Autowired
	private AdvogadoServico advogadoServico;
	@Autowired
	private PessoaFisicaServico pessoaFisicaServico;
	@Autowired
	private PessoaJuridicaServico pessoaJuridicaServico;
	@Autowired
	private FaseServico faseServico;
	@Autowired
	private MateriaServico materiaServico;

	@Autowired
	private LoginController loginController;

	private Login loginLogado;

	private List<Processo> processosFiltrados;
	private List<Boolean> listToggle = Arrays.asList(true, true, true, true, true, true, true, true, true, true, true,
			true, true, true, true);

	public void iniciarBean() {
		atualizarListToogleGrid();
		atualizarListaProcessos();
	}

	public void atualizarListToogleGrid() {
		// listToggle =
		// loginLogado.columnVisibleDataTable(EnumPaginas.ADVOGADO.toString(),
		// 5);
	}

	public void atualizarListaProcessos() {
		// this.processos = processoServico.listarTodos();

		this.lazyProcessoDataTable = processoServico.getLazyProcessoDataTable();
	}

	public void novoProcesso() {
		processoNulo();
		this.processo = new Processo();
		this.resetFormCidade();
		Mensagem.mensagemInformacao(null, null, "Novo Processo!");
	}

	public void novoProcessoCancelar() {
		if (this.processo.getProcesso() != null) {
			Mensagem.mensagemInformacao(null, null, "A edição do Processo foi cancelada com sucesso!");
		} else
			Mensagem.mensagemInformacao(null, null, "A inclusão do Processo foi cancelada com sucesso!");
		processoNulo();
	}

	public void processoNulo() {
		this.processo = null;
	}

	public void handleFileUpload(FileUploadEvent event) {
		if (!this.processo.getProcesso().isEmpty()) {

			String folder = "E:\\srsc_advogado\\arquivos\\processos\\" + this.processo.getProcesso() + "\\";

			File result = new File(folder);
			if (!result.exists()) {
				result.mkdirs();
			}
			result = new File(folder + "01.pdf");
			System.out.println(folder + event.getFile().getFileName());

			try {
				FileOutputStream fileOutputStream = new FileOutputStream(result);
				Integer tamanhoBuffer = (int) event.getFile().getSize();
				byte[] buffer = new byte[tamanhoBuffer];

				int bulk;
				InputStream inputStream = event.getFile().getInputstream();
				while (true) {
					bulk = inputStream.read(buffer);
					if (bulk < 0) {
						break;
					}
					fileOutputStream.write(buffer, 0, bulk);
					fileOutputStream.flush();
				}

				fileOutputStream.close();
				inputStream.close();

				this.processo.setArquivo(folder + "01.pdf");
				this.processo.setDataArquivo(new Date());

				FacesMessage msg = new FacesMessage("File Description",
						"file name: " + event.getFile().getFileName() + "<br/>File size: "
								+ event.getFile().getSize() / 1024 + " Kb<br/>Content type: "
								+ event.getFile().getContentType() + "<br/><br/>The file was uploaded.");
				FacesContext.getCurrentInstance().addMessage(null, msg);

			} catch (IOException e) {
				e.printStackTrace();

				FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR, "The files were not uploaded!", "");
				FacesContext.getCurrentInstance().addMessage(null, error);
			}
		} else
			Mensagem.mensagemInformacao(null, null, "O nº do processo deve estar preenchido!");
	}

	public void exluirPDF() {
		try {
			File file = new File(this.processo.getArquivo());

			if (file.delete()) {
				this.processo.setArquivo(null);
				this.processo.setDataArquivo(null);
				Mensagem.mensagemInformacao(null, null, "PDF excluido com sucesso!");
			} else {
				Mensagem.mensagemAlerta(null, null, "Falha na operação de exclusão!");
			}
		} catch (Exception e) {
			Mensagem.mensagemErro(null, null, "Erro ao excluir o PDF!");
		}
	}

	public void showPDF() {
		// HttpServletRequest request = (HttpServletRequest)
		// FacesContext.getCurrentInstance().getExternalContext().getRequest();
		// RequestContext context = RequestContext.getCurrentInstance();
		String processo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("processo");

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/srsc_advogado/showPDF/" + processo + "/");
			// context.execute("window.open('/showPDF/" + processo + "/',
			// '_newtab')");
		} catch (IOException e) {
			Mensagem.mensagemErro(null, null, "Erro ao redirecionar página!");
		} finally {
			Mensagem.mensagemErro(null, null, "Erro ao redirecionar página!");
		}
	}

	public void salvar() {
		if (this.processo.getAdvAutor() != null) {
			List<Advogado> adv = new ArrayList<Advogado>();
			for (Advogado advogado : this.processo.getAdvAutor()) {
				if (advogado.getId() != null)
					adv.add(advogadoServico.obterPorId(advogado.getId()));
			}
			if (this.processo.getAdvAutor().size() == adv.size())
				this.processo.setAdvAutor(adv);
		}

		if (this.processo.getAutores() != null) {
			List<Pessoa> pes = new ArrayList<Pessoa>();
			for (Pessoa pessoa : this.processo.getAutores()) {
				if (pessoa.getId() != null)
					pes.add(pessoaFisicaServico.obterPorId(pessoa.getId()));
			}
			if (this.processo.getAutores().size() == pes.size())
				this.processo.setAutores(pes);
		}

		if (this.processo.getAdvReu() != null) {
			List<Advogado> adv = new ArrayList<Advogado>();
			for (Advogado advogado : this.processo.getAdvReu()) {
				if (advogado.getId() != null)
					adv.add(advogadoServico.obterPorId(advogado.getId()));
			}
			if (this.processo.getAdvReu().size() == adv.size())
				this.processo.setAdvReu(adv);
		}

		if (this.processo.getReus() != null) {
			List<Pessoa> pes = new ArrayList<Pessoa>();
			for (Pessoa pessoa : this.processo.getReus()) {
				if (pessoa.getId() != null)
					pes.add(pessoaJuridicaServico.obterPorId(pessoa.getId()));
			}
			if (this.processo.getReus().size() == pes.size())
				this.processo.setReus(pes);
		}

		if (this.processo.getFases() != null) {
			List<Fase> fs = new ArrayList<Fase>();
			for (Fase fase : this.processo.getFases()) {
				if (fase.getId() != null)
					fs.add(faseServico.obterPorId(fase.getId()));
			}
			if (this.processo.getFases().size() == fs.size())
				this.processo.setFases(fs);
		}

		if (this.processo.getMaterias() != null) {
			List<Materia> mt = new ArrayList<Materia>();
			for (Materia materia : this.processo.getMaterias()) {
				if (materia.getId() != null)
					mt.add(materiaServico.obterPorId(materia.getId()));
			}
			if (this.processo.getMaterias().size() == mt.size())
				this.processo.setMaterias(mt);
		}

		// this.processo.setLogin(this.getLoginLogado());
		this.processo.setDataCadastro(new Date());
		this.processoServico.salvar(this.processo);
		atualizarListaProcessos();
		processoNulo();
		Mensagem.mensagemInformacao(null, null, "Processo salvo com sucesso!");
	}

	public void editar() {
		if (this.processo != null) {
			// this.processo = processoServico.obterPorId(this.processo);

			this.resetFormCidade();
			Mensagem.mensagemInformacao(null, null, "Processo em modo de Edição!");
		}
	}

	public void deletar() {
		// this.processo = processoServico.obterPorId(this.processo);
		exluirPDF();
		this.processoServico.excluir(this.processo);
		if (this.processosFiltrados != null)
			this.processosFiltrados.remove(this.processo);
		processoNulo();
		atualizarListaProcessos();
		Mensagem.mensagemInformacao(null, null, "Processo foi desativado com sucesso!");
	}

	public Date dataMinima() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -6);
		return calendar.getTime();
	}

	public String getDataAtual() {
		return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
	}

	public void onRowSelect(final SelectEvent event) {
		br.com.project.modelo.dataTable.Processo processo = (br.com.project.modelo.dataTable.Processo) event
				.getObject();
		this.processo = processoServico.obterPorId(processo.getProcesso());
	}

	public void onRowDblselect() {
		// this.processo = (Processo) event.getObject();
		this.editar();
	}

	public void resetFormCidade() {
		PrimeFaces.current().resetInputs("DialogProcessoForm");
	}

	public void onToggle(ToggleEvent e) {
		listToggle.clear();
		listToggle.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
	}

	public PerfilPreConfiguracao getPerfilPreConfiguracaoPage() {
		return loginController.obterPerfilPreConfiguracaoLoginPage(EnumPaginas.PROCESSO);
	}

	public List<String> listJuizes(String juiz) {
		return (List<String>) processoServico.obterJuizes(juiz);
	}

	public List<String> listTrtRelator(String trtRelator) {
		return (List<String>) processoServico.obterTrtRelator(trtRelator);
	}

	public List<String> listTstRelator(String tstRelator) {
		return (List<String>) processoServico.obterTstRelator(tstRelator);
	}

	public List<Advogado> listAdvogados(String advogado) {
		return (List<Advogado>) advogadoServico.obterAdvogado(advogado);
	}

	public List<PessoaFisica> listPessoaFisicas(String pessoaFisica) {
		return (List<PessoaFisica>) pessoaFisicaServico.obterPessoaFisica(pessoaFisica);
	}

	public List<PessoaJuridica> listPessoaJuridicas(String pessoaJuridica) {
		return (List<PessoaJuridica>) pessoaJuridicaServico.obterPessoaJuridica(pessoaJuridica);
	}

	public List<Fase> listFases(String fase) {
		List<Fase> fases = (List<Fase>) faseServico.obterFase(fase);
		return fases;
	}

	public List<Materia> listMaterias(String materia) {
		List<Materia> materias = (List<Materia>) materiaServico.obterMateria(materia);
		return materias;
	}

	public Processo getProcesso() {
		return processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	public List<br.com.project.modelo.dataTable.Processo> getLazyProcessoDataTable() {
		return lazyProcessoDataTable;
	}

	public ProcessoServico getProcessoServico() {
		return processoServico;
	}

	public void setProcessoServico(ProcessoServico processoServico) {
		this.processoServico = processoServico;
	}

	public Login getLoginLogado() {
		setLoginLogado();
		return loginLogado;
	}

	public void setLoginLogado() {
		this.loginLogado = loginController.obterLoginLogado();
	}

	public LoginController getLoginController() {
		return loginController;
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}

	public List<Processo> getProcessosFiltrados() {
		return processosFiltrados;
	}

	public void setProcessosFiltrados(List<Processo> processosFiltrados) {
		this.processosFiltrados = processosFiltrados;
	}

	public List<Boolean> getListToggle() {
		return listToggle;
	}

	public void setListToggle(List<Boolean> listToggle) {
		this.listToggle = listToggle;
	}
}
