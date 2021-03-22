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
import br.com.project.modelo.PerfilConfiguracao;
import br.com.project.modelo.PerfilPreConfiguracao;
import br.com.project.servico.PerfilConfiguracaoServico;
import br.com.project.util.Mensagem;

@Controller(value = "perfilConfiguracaoBean")
@Scope(value = "session")
public class PerfilConfiguracaoBean implements Serializable {

	private static final long serialVersionUID = 2697264978280707957L;

	private PerfilConfiguracao perfilConfiguracao;
	private List<PerfilConfiguracao> perfilConfiguracaos = new ArrayList<PerfilConfiguracao>();

	@Autowired
	private PerfilConfiguracaoServico perfilConfiguracaoServico;

	@Autowired
	private LoginController loginController;

	private Login loginLogado;

	private List<EnumEstado> estados = Arrays.asList(EnumEstado.values());
	private List<PerfilConfiguracao> perfilConfiguracaosFiltrados;
	private List<Boolean> listToggle = Arrays.asList(true, true, true, true, true);

	public void iniciarBean() {
		atualizarListToogleGrid();
		atualizarListaPerfilConfiguracaos();
	}

	public void atualizarListToogleGrid() {
		// listToggle = loginLogado.columnVisibleDataTable(EnumPaginas.ADVOGADO.toString(), 5);
	}

	public void atualizarListaPerfilConfiguracaos() {
		this.perfilConfiguracaos = perfilConfiguracaoServico.listarTodos();
	}

	public void novoPerfilConfiguracao() {
		this.perfilConfiguracao = new PerfilConfiguracao();
		this.resetFormCidade();
		Mensagem.mensagemErro(null, null, "Novo PerfilConfiguracao!");
	}

	public void novoPerfilConfiguracaoCancelar() {
		if (this.perfilConfiguracao.getId() != null) {
			Mensagem.mensagemErro(null, null, "A edição do PerfilConfiguracao foi cancelada com sucesso!");
		} else
			Mensagem.mensagemErro(null, null, "A inclusão do PerfilConfiguracao foi cancelada com sucesso!");
		this.perfilConfiguracao = null;
	}

	public void perfilConfiguracaoNulo() {
		this.perfilConfiguracao = null;
	}

	public void salvar() {
		this.perfilConfiguracaoServico.salvar(this.perfilConfiguracao);
		atualizarListaPerfilConfiguracaos();
		this.perfilConfiguracao = null;
		Mensagem.mensagemErro(null, null, "PerfilConfiguracao salvo com sucesso!");
	}

	public void editar() {
		if (this.perfilConfiguracao != null) {
			this.perfilConfiguracao = perfilConfiguracaoServico.obterPorId(this.perfilConfiguracao.getId());
			this.resetFormCidade();
			Mensagem.mensagemErro(null, null, "PerfilConfiguracao em modo de Edição!");
		}
	}

	public void deletar() {
		this.perfilConfiguracao = perfilConfiguracaoServico.obterPorId(this.perfilConfiguracao.getId());
		this.perfilConfiguracaoServico.excluir(this.perfilConfiguracao);
		if (this.perfilConfiguracaosFiltrados != null)
			this.perfilConfiguracaosFiltrados.remove(this.perfilConfiguracao);
		this.perfilConfiguracao = null;
		atualizarListaPerfilConfiguracaos();
		Mensagem.mensagemErro(null, null, "PerfilConfiguracao foi desativado com sucesso!");
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
		this.perfilConfiguracao = (PerfilConfiguracao) event.getObject();
		this.editar();
	}

	public void resetFormCidade() {
		PrimeFaces.current().resetInputs("DialogPerfilConfiguracaoForm");
	}

	public void onToggle(ToggleEvent e) {
		listToggle.clear();
		listToggle.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
	}

	public PerfilPreConfiguracao getPerfilPreConfiguracaoPage() {
		return loginController.obterPerfilPreConfiguracaoLoginPage(EnumPaginas.ADVOGADO);
	}

	public PerfilConfiguracao getPerfilConfiguracao() {
		return perfilConfiguracao;
	}

	public void setPerfilConfiguracao(PerfilConfiguracao perfilConfiguracao) {
		this.perfilConfiguracao = perfilConfiguracao;
	}

	public List<PerfilConfiguracao> getPerfilConfiguracaos() {
		return perfilConfiguracaos;
	}

	public void setPerfilConfiguracaos(List<PerfilConfiguracao> perfilConfiguracaos) {
		this.perfilConfiguracaos = perfilConfiguracaos;
	}

	public PerfilConfiguracaoServico getPerfilConfiguracaoServico() {
		return perfilConfiguracaoServico;
	}

	public void setPerfilConfiguracaoServico(PerfilConfiguracaoServico perfilConfiguracaoServico) {
		this.perfilConfiguracaoServico = perfilConfiguracaoServico;
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

	public List<PerfilConfiguracao> getPerfilConfiguracaosFiltrados() {
		return perfilConfiguracaosFiltrados;
	}

	public void setPerfilConfiguracaosFiltrados(List<PerfilConfiguracao> perfilConfiguracaosFiltrados) {
		this.perfilConfiguracaosFiltrados = perfilConfiguracaosFiltrados;
	}

	public List<Boolean> getListToggle() {
		return listToggle;
	}

	public void setListToggle(List<Boolean> listToggle) {
		this.listToggle = listToggle;
	}
}
