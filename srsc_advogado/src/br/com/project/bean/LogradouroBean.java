package br.com.project.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
import br.com.project.modelo.Bairro;
import br.com.project.modelo.Cidade;
import br.com.project.modelo.EnumEstado;
import br.com.project.modelo.EnumPaginas;
import br.com.project.modelo.EnumTipoLogradouro;
import br.com.project.modelo.Login;
import br.com.project.modelo.Logradouro;
import br.com.project.modelo.PerfilPreConfiguracao;
import br.com.project.servico.BairroServico;
import br.com.project.servico.CidadeServico;
import br.com.project.servico.LogradouroServico;
import br.com.project.util.Mensagem;

@Controller(value = "logradouroBean")
@Scope(value = "session")
public class LogradouroBean implements Serializable {

	private static final long serialVersionUID = 2697264978280707957L;

	private Logradouro logradouro;
//	private List<Logradouro> logradouros = new ArrayList<Logradouro>();
	private List<br.com.project.modelo.dataTable.Logradouro> lazyLogradouroDataTable;

	@Autowired
	private LogradouroServico logradouroServico;

	@Autowired
	private BairroServico bairroServico;

	@Autowired
	private CidadeServico cidadeServico;

	@Autowired
	private LoginController loginController;

	private Login loginLogado;

	private List<EnumEstado> estados = Arrays.asList(EnumEstado.values());
	private List<EnumTipoLogradouro> tipoLogradouros = Arrays.asList(EnumTipoLogradouro.values());
	private List<Logradouro> logradourosFiltrados;
	private List<Boolean> listToggle = Arrays.asList(true, true, true, true, true);

	public void iniciarBean() {
		atualizarListToogleGrid();
		atualizarListaLogradouros();
		this.setLoginLogado(loginController.obterLoginLogado());
	}

	public void atualizarListToogleGrid() {
		// listToggle =
		// loginLogado.columnVisibleDataTable(EnumPaginas.LOGRADOURO.toString(),
		// 5);
	}

	public void atualizarListaLogradouros() {
//		this.logradouros = logradouroServico.listarTodos();
		this.lazyLogradouroDataTable = logradouroServico.getLazyLogradouroDataTable();
	}

	public void novoLogradouro() {
		this.logradouro = new Logradouro();
		this.resetFormCidade();
		Mensagem.mensagemErro(null, null, "Novo Logradouro!");
	}

	public void novoLogradouroCancelar() {
		if (this.logradouro.getId() != null) {
			Mensagem.mensagemErro(null, null, "A edição do Logradouro foi cancelada com sucesso!");
		} else
			Mensagem.mensagemErro(null, null, "A inclusão do Logradouro foi cancelada com sucesso!");
		logradouroNulo();
	}

	public void logradouroNulo() {
		this.logradouro = null;
	}

	public void salvar() {
		this.logradouro.setDataCadastro(new Date());
		this.logradouro.setLogin(loginLogado);
		this.logradouroServico.salvar(this.logradouro);
		atualizarListaLogradouros();
		logradouroNulo();
		Mensagem.mensagemErro(null, null, "Logradouro salvo com sucesso!");
	}

	public void editar() {
		if (this.logradouro != null) {
//			this.logradouro = logradouroServico.obterPorId(this.logradouro.getId());
			this.resetFormCidade();
			Mensagem.mensagemErro(null, null, "Logradouro em modo de Edição!");
		}
	}

	public void deletar() {
//		this.logradouro = logradouroServico.obterPorId(this.logradouro.getId());
		this.logradouroServico.excluir(this.logradouro);
		if (this.logradourosFiltrados != null)
			this.logradourosFiltrados.remove(this.logradouro);
		this.logradouro = null;
		atualizarListaLogradouros();
		Mensagem.mensagemErro(null, null, "Logradouro foi desativado com sucesso!");
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
		br.com.project.modelo.dataTable.Logradouro logradouroDataTable = (br.com.project.modelo.dataTable.Logradouro) event.getObject();
		this.logradouro = logradouroServico.obterPorId(logradouroDataTable.getId());
	}

	public void onRowDblselect() {
//		this.logradouro = (Logradouro) event.getObject();
		this.editar();
	}

	public void resetFormCidade() {
		PrimeFaces.current().resetInputs("DialogLogradouroForm");
	}

	public void onToggle(ToggleEvent e) {
		listToggle.clear();
		listToggle.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
	}

	public List<Cidade> getCidadesDoEstado() {
		return cidadeServico.obterCidadesDoEstado(this.logradouro.getBairro().getCidade().getEstado());
	}

	public List<Bairro> getBairrosDaCidade() {
		return bairroServico.obterBairrosDaCidade(this.logradouro.getBairro().getCidade());
	}

	public PerfilPreConfiguracao getPerfilPreConfiguracaoPage() {
		return loginController.obterPerfilPreConfiguracaoLoginPage(EnumPaginas.ADVOGADO);
	}

	public Logradouro getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(Logradouro logradouro) {
		this.logradouro = logradouro;
	}

	public List<br.com.project.modelo.dataTable.Logradouro> getLazyLogradouroDataTable() {
		return lazyLogradouroDataTable;
	}

	public LogradouroServico getLogradouroServico() {
		return logradouroServico;
	}

	public void setLogradouroServico(LogradouroServico logradouroServico) {
		this.logradouroServico = logradouroServico;
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

	public List<EnumTipoLogradouro> getTipoLogradouros() {
		return tipoLogradouros;
	}

	public void setTipoLogradouros(List<EnumTipoLogradouro> tipoLogradouros) {
		this.tipoLogradouros = tipoLogradouros;
	}

	public List<EnumEstado> getEstados() {
		return estados;
	}

	public void setEstados(List<EnumEstado> estados) {
		this.estados = estados;
	}

	public List<Logradouro> getLogradourosFiltrados() {
		return logradourosFiltrados;
	}

	public void setLogradourosFiltrados(List<Logradouro> logradourosFiltrados) {
		this.logradourosFiltrados = logradourosFiltrados;
	}

	public List<Boolean> getListToggle() {
		return listToggle;
	}

	public void setListToggle(List<Boolean> listToggle) {
		this.listToggle = listToggle;
	}
}
