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
import br.com.project.modelo.Login;
import br.com.project.modelo.PerfilPreConfiguracao;
import br.com.project.servico.PerfilPreConfiguracaoServico;
import br.com.project.util.Mensagem;

@Controller(value = "perfilPreConfiguracaoBean")
@Scope(value = "session")
public class PerfilPreConfiguracaoBean implements Serializable {

	private static final long serialVersionUID = 2697264978280707957L;

	private PerfilPreConfiguracao perfilPreConfiguracao;
	private List<PerfilPreConfiguracao> perfilPreConfiguracaos = new ArrayList<PerfilPreConfiguracao>();

	@Autowired
	private PerfilPreConfiguracaoServico perfilPreConfiguracaoServico;

	@Autowired
	private LoginController loginController;

	private Login loginLogado;

	private List<EnumEstado> estados = Arrays.asList(EnumEstado.values());
	private List<PerfilPreConfiguracao> perfilPreConfiguracaosFiltrados;
	private List<Boolean> listToggle = Arrays.asList(true, true, true, true, true);

	public void iniciarBean() {
		atualizarListToogleGrid();
		atualizarListaPerfilPreConfiguracaos();
	}

	public void atualizarListToogleGrid() {
		// listToggle = loginLogado.columnVisibleDataTable(EnumPaginas.ADVOGADO.toString(), 5);
	}

	public void atualizarListaPerfilPreConfiguracaos() {
		this.perfilPreConfiguracaos = perfilPreConfiguracaoServico.listarTodos();
	}

	public void novoPerfilPreConfiguracao() {
		this.perfilPreConfiguracao = new PerfilPreConfiguracao();
		this.resetFormCidade();
		Mensagem.mensagemErro(null, null, "Novo PerfilPreConfiguracao!");
	}

	public void novoPerfilPreConfiguracaoCancelar() {
		if (this.perfilPreConfiguracao.getId() != null) {
			Mensagem.mensagemErro(null, null, "A edição do PerfilPreConfiguracao foi cancelada com sucesso!");
		} else
			Mensagem.mensagemErro(null, null, "A inclusão do PerfilPreConfiguracao foi cancelada com sucesso!");
		this.perfilPreConfiguracao = null;
	}

	public void perfilPreConfiguracaoNulo() {
		this.perfilPreConfiguracao = null;
	}

	public void salvar() {
		this.perfilPreConfiguracaoServico.salvar(this.perfilPreConfiguracao);
		atualizarListaPerfilPreConfiguracaos();
		this.perfilPreConfiguracao = null;
		Mensagem.mensagemErro(null, null, "PerfilPreConfiguracao salvo com sucesso!");
	}

	public void editar() {
		if (this.perfilPreConfiguracao != null) {
			this.perfilPreConfiguracao = perfilPreConfiguracaoServico.obterPorId(this.perfilPreConfiguracao.getId());
			this.resetFormCidade();
			Mensagem.mensagemErro(null, null, "PerfilPreConfiguracao em modo de Edição!");
		}
	}

	public void deletar() {
		this.perfilPreConfiguracao = perfilPreConfiguracaoServico.obterPorId(this.perfilPreConfiguracao.getId());
		this.perfilPreConfiguracaoServico.excluir(this.perfilPreConfiguracao);
		if (this.perfilPreConfiguracaosFiltrados != null)
			this.perfilPreConfiguracaosFiltrados.remove(this.perfilPreConfiguracao);
		this.perfilPreConfiguracao = null;
		atualizarListaPerfilPreConfiguracaos();
		Mensagem.mensagemErro(null, null, "PerfilPreConfiguracao foi desativado com sucesso!");
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
		this.perfilPreConfiguracao = (PerfilPreConfiguracao) event.getObject();
		this.editar();
	}

	public void resetFormCidade() {
		PrimeFaces.current().resetInputs("DialogPerfilPreConfiguracaoForm");
	}

	public void onToggle(ToggleEvent e) {
		listToggle.clear();
		listToggle.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
	}

	public PerfilPreConfiguracao getPerfilPreConfiguracaoPage() {
		return loginController.obterPerfilPreConfiguracaoLoginPage(EnumPaginas.ADVOGADO);
	}

	public PerfilPreConfiguracao getPerfilPreConfiguracao() {
		return perfilPreConfiguracao;
	}

	public void setPerfilPreConfiguracao(PerfilPreConfiguracao perfilPreConfiguracao) {
		this.perfilPreConfiguracao = perfilPreConfiguracao;
	}

	public List<PerfilPreConfiguracao> getPerfilPreConfiguracaos() {
		return perfilPreConfiguracaos;
	}

	public void setPerfilPreConfiguracaos(List<PerfilPreConfiguracao> perfilPreConfiguracaos) {
		this.perfilPreConfiguracaos = perfilPreConfiguracaos;
	}

	public PerfilPreConfiguracaoServico getPerfilPreConfiguracaoServico() {
		return perfilPreConfiguracaoServico;
	}

	public void setPerfilPreConfiguracaoServico(PerfilPreConfiguracaoServico perfilPreConfiguracaoServico) {
		this.perfilPreConfiguracaoServico = perfilPreConfiguracaoServico;
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

	public List<EnumEstado> getEstados() {
		return estados;
	}

	public void setEstados(List<EnumEstado> estados) {
		this.estados = estados;
	}

	public List<PerfilPreConfiguracao> getPerfilPreConfiguracaosFiltrados() {
		return perfilPreConfiguracaosFiltrados;
	}

	public void setPerfilPreConfiguracaosFiltrados(List<PerfilPreConfiguracao> perfilPreConfiguracaosFiltrados) {
		this.perfilPreConfiguracaosFiltrados = perfilPreConfiguracaosFiltrados;
	}

	public List<Boolean> getListToggle() {
		return listToggle;
	}

	public void setListToggle(List<Boolean> listToggle) {
		this.listToggle = listToggle;
	}
}
