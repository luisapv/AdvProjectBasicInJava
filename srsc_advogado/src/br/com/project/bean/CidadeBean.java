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
import br.com.project.modelo.Cidade;
import br.com.project.modelo.EnumEstado;
import br.com.project.modelo.EnumPaginas;
import br.com.project.modelo.Login;
import br.com.project.modelo.PerfilPreConfiguracao;
import br.com.project.servico.CidadeServico;
import br.com.project.util.Mensagem;

@Controller(value = "cidadeBean")
@Scope(value = "session")
public class CidadeBean implements Serializable {

	private static final long serialVersionUID = 2697264978280707957L;

	private Cidade cidade;
	private List<Cidade> cidades = new ArrayList<Cidade>();

	@Autowired
	private CidadeServico cidadeServico;

	@Autowired
	private LoginController loginController;

	private Login loginLogado;

	private List<EnumEstado> estados = Arrays.asList(EnumEstado.values());
	private List<Cidade> cidadesFiltrados;
	private List<Boolean> listToggle = Arrays.asList(true, true, true, true, true);

	public void iniciarBean() {
		atualizarListToogleGrid();
		atualizarListaCidades();
		this.setLoginLogado(loginController.obterLoginLogado());
	}

	public void atualizarListToogleGrid() {
		// listToggle = loginLogado.columnVisibleDataTable(EnumPaginas.ADVOGADO.toString(), 5);
	}

	public void atualizarListaCidades() {
		this.cidades = cidadeServico.listarTodos();
	}

	public void novoCidade() {
		this.cidade = new Cidade();
		this.resetFormCidade();
		Mensagem.mensagemInformacao(null, null, "Novo Cidade!");
	}

	public void novoCidadeCancelar() {
		if (this.cidade.getId() != null) {
			Mensagem.mensagemInformacao(null, null, "A edição do Cidade foi cancelada com sucesso!");
		} else
			Mensagem.mensagemInformacao(null, null, "A inclusão do Cidade foi cancelada com sucesso!");
		this.cidade = null;
	}

	public void cidadeNulo() {
		this.cidade = null;
	}

	public void salvar() {
		this.cidade.setDataCadastro(new Date());
		this.cidade.setLogin(loginLogado);
		this.cidadeServico.salvar(this.cidade);
		atualizarListaCidades();
		this.cidade = null;
		Mensagem.mensagemInformacao(null, null, "Cidade salvo com sucesso!");
	}

	public void editar() {
		if (this.cidade != null) {
			this.cidade = cidadeServico.obterPorId(this.cidade.getId());
			this.resetFormCidade();
			Mensagem.mensagemInformacao(null, null, "Cidade em modo de Edição!");
		}
	}

	public void deletar() {
		this.cidade = cidadeServico.obterPorId(this.cidade.getId());
		this.cidadeServico.excluir(this.cidade);
		if (this.cidadesFiltrados != null)
			this.cidadesFiltrados.remove(this.cidade);
		this.cidade = null;
		atualizarListaCidades();
		Mensagem.mensagemInformacao(null, null, "Cidade foi desativado com sucesso!");
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
		this.cidade = (Cidade) event.getObject();
		this.editar();
	}

	public void resetFormCidade() {
		PrimeFaces.current().resetInputs("DialogCidadeForm");
	}

	public void onToggle(ToggleEvent e) {
		listToggle.clear();
		listToggle.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
	}

	public PerfilPreConfiguracao getPerfilPreConfiguracaoPage() {
		return loginController.obterPerfilPreConfiguracaoLoginPage(EnumPaginas.ADVOGADO);
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public CidadeServico getCidadeServico() {
		return cidadeServico;
	}

	public void setCidadeServico(CidadeServico cidadeServico) {
		this.cidadeServico = cidadeServico;
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

	public List<Cidade> getCidadesFiltrados() {
		return cidadesFiltrados;
	}

	public void setCidadesFiltrados(List<Cidade> cidadesFiltrados) {
		this.cidadesFiltrados = cidadesFiltrados;
	}

	public List<Boolean> getListToggle() {
		return listToggle;
	}

	public void setListToggle(List<Boolean> listToggle) {
		this.listToggle = listToggle;
	}
}
