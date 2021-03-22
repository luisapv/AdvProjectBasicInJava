package br.com.project.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.project.control.LoginController;
import br.com.project.modelo.EnumEstado;
import br.com.project.modelo.EnumPaginas;
import br.com.project.modelo.Fase;
import br.com.project.modelo.Login;
import br.com.project.modelo.PerfilPreConfiguracao;
import br.com.project.servico.FaseServico;
import br.com.project.util.Mensagem;

@Controller(value = "faseBean")
@Scope(value = "session")
public class FaseBean implements Serializable {

	private static final long serialVersionUID = 2697264978280707957L;

	private Fase fase;
	private List<Fase> fases = new ArrayList<Fase>();

	@Autowired
	private FaseServico faseServico;

	@Autowired
	private LoginController loginController;

	private Login loginLogado;

	private List<EnumEstado> estados = Arrays.asList(EnumEstado.values());
	private List<Fase> fasesFiltrados;
	private List<Boolean> listToggle = Arrays.asList(true, true, true, true, true);

	public void iniciarBean() {
		atualizarListToogleGrid();
		atualizarListaFases();
		this.setLoginLogado(loginController.obterLoginLogado());
	}

	public void atualizarListToogleGrid() {
		// listToggle = loginLogado.columnVisibleDataTable(EnumPaginas.ADVOGADO.toString(), 5);
	}

	public void atualizarListaFases() {
		this.fases = faseServico.listarTodos();
	}

	public void novoFase() {
		this.fase = new Fase();
		this.resetFormCidade();
		Mensagem.mensagemErro(null, null, "Novo Fase!");
	}

	public void novoFaseCancelar() {
		if (this.fase.getId() != null) {
			Mensagem.mensagemErro(null, null, "A edição do Fase foi cancelada com sucesso!");
		} else
			Mensagem.mensagemErro(null, null, "A inclusão do Fase foi cancelada com sucesso!");
		this.fase = null;
	}

	public void faseNulo() {
		this.fase = null;
	}

	public void salvar() {
		this.fase.setDataCadastro(new Date());
		this.fase.setLogin(loginLogado);
		this.faseServico.salvar(this.fase);
		atualizarListaFases();
		this.fase = null;
		Mensagem.mensagemErro(null, null, "Fase salvo com sucesso!");
	}

	public void editar() {
		if (this.fase != null) {
			this.fase = faseServico.obterPorId(this.fase.getId());
			this.resetFormCidade();
			Mensagem.mensagemErro(null, null, "Fase em modo de Edição!");
		}
	}

	public void deletar() {
		this.fase = faseServico.obterPorId(this.fase.getId());
		this.faseServico.excluir(this.fase);
		if (this.fasesFiltrados != null)
			this.fasesFiltrados.remove(this.fase);
		this.fase = null;
		atualizarListaFases();
		Mensagem.mensagemErro(null, null, "Fase foi desativado com sucesso!");
	}

	public Date dataMinima() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -6);
		return calendar.getTime();
	}

	public String getDataAtual() {
		return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
	}

	public void onRowDblselect(final SelectEvent event) {
		this.fase = (Fase) event.getObject();
		this.editar();
	}

	public void resetFormCidade() {
		PrimeFaces.current().resetInputs("DialogFaseForm");
	}

	public void onToggle(ToggleEvent e) {
		listToggle.clear();
		listToggle.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
	}

	public PerfilPreConfiguracao getPerfilPreConfiguracaoPage() {
		return loginController.obterPerfilPreConfiguracaoLoginPage(EnumPaginas.ADVOGADO);
	}

	public Fase getFase() {
		return fase;
	}

	public void setFase(Fase fase) {
		this.fase = fase;
	}

	public List<Fase> getFases() {
		return fases;
	}

	public void setFases(List<Fase> fases) {
		this.fases = fases;
	}

	public FaseServico getFaseServico() {
		return faseServico;
	}

	public void setFaseServico(FaseServico faseServico) {
		this.faseServico = faseServico;
	}

	public Login getLoginLogado() {
		return loginLogado;
	}

	public void setLoginLogado(Login login) {
		this.loginLogado = login;
	}

	public LoginController getLoginController() {
		return loginController;
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}

	public List<EnumEstado> getEstados() {
		return estados;
	}

	public void setEstados(List<EnumEstado> estados) {
		this.estados = estados;
	}

	public List<Fase> getFasesFiltrados() {
		return fasesFiltrados;
	}

	public void setFasesFiltrados(List<Fase> fasesFiltrados) {
		this.fasesFiltrados = fasesFiltrados;
	}

	public List<Boolean> getListToggle() {
		return listToggle;
	}

	public void setListToggle(List<Boolean> listToggle) {
		this.listToggle = listToggle;
	}
}
