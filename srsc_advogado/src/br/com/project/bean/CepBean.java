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
import br.com.project.modelo.Cep;
import br.com.project.modelo.Cidade;
import br.com.project.modelo.EnumEstado;
import br.com.project.modelo.EnumPaginas;
import br.com.project.modelo.Login;
import br.com.project.modelo.Logradouro;
import br.com.project.modelo.PerfilPreConfiguracao;
import br.com.project.servico.BairroServico;
import br.com.project.servico.CepServico;
import br.com.project.servico.CidadeServico;
import br.com.project.servico.LogradouroServico;
import br.com.project.util.Mensagem;

@Controller(value = "cepBean")
@Scope(value = "session")
public class CepBean implements Serializable {

	private static final long serialVersionUID = 2697264978280707957L;

	private Cep cep;
//	private List<Cep> ceps = new ArrayList<Cep>();
	private List<br.com.project.modelo.dataTable.Cep> lazyCepDataTable;

	@Autowired
	private CepServico cepServico;
	@Autowired
	private CidadeServico cidadeServico;
	@Autowired
	private BairroServico bairroServico;
	@Autowired
	private LogradouroServico logradouroServico;

	@Autowired
	private LoginController loginController;

	private Login loginLogado;

	private List<EnumEstado> estados = Arrays.asList(EnumEstado.values());
	private List<Cep> cepsFiltrados;
	private List<Boolean> listToggle = Arrays.asList(true, true, true, true, true);

	public void iniciarBean() {
		atualizarListToogleGrid();
		atualizarListaCeps();
		this.setLoginLogado(loginController.obterLoginLogado());
	}

	public void atualizarListToogleGrid() {
		// listToggle = loginLogado.columnVisibleDataTable(EnumPaginas.ADVOGADO.toString(), 5);
	}

	public void atualizarListaCeps() {
//		this.ceps = cepServico.listarTodos();
		this.lazyCepDataTable = cepServico.getLazyCepDataTable();
	}

	public void novoCep() {
		this.cep = new Cep();
		this.resetFormCidade();
		Mensagem.mensagemInformacao(null, null, "Novo Cep!");
	}

	public void novoCepCancelar() {
		if (this.cep.getId() != null) {
			Mensagem.mensagemInformacao(null, null, "A edição do Cep foi cancelada com sucesso!");
		} else
			Mensagem.mensagemInformacao(null, null, "A inclusão do Cep foi cancelada com sucesso!");
		this.cep = null;
	}

	public void cepNulo() {
		this.cep = null;
	}

	public void salvar() {
		this.cep.setDataCadastro(new Date());
		this.cep.setLogin(loginLogado);
		this.cepServico.salvar(this.cep);
		atualizarListaCeps();
		this.cep = null;
		Mensagem.mensagemInformacao(null, null, "Cep salvo com sucesso!");
	}

	public void editar() {
		if (this.cep != null) {
//			this.cep = cepServico.obterPorId(this.cep.getId());
			this.resetFormCidade();
			Mensagem.mensagemInformacao(null, null, "Cep em modo de Edição!");
		}
	}

	public void deletar() {
//		this.cep = cepServico.obterPorId(this.cep.getId());
		this.cepServico.excluir(this.cep);
		if (this.cepsFiltrados != null)
			this.cepsFiltrados.remove(this.cep);
		this.cep = null;
		atualizarListaCeps();
		Mensagem.mensagemInformacao(null, null, "Cep foi desativado com sucesso!");
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
		br.com.project.modelo.dataTable.Cep cepDataTable = (br.com.project.modelo.dataTable.Cep) event.getObject();
		this.cep = cepServico.obterPorId(cepDataTable.getId());
	}

	public void onRowDblselect() {
//		this.cep = (Cep) event.getObject();
		this.editar();
	}

	public void resetFormCidade() {
		PrimeFaces.current().resetInputs("DialogCepForm");
	}

	public void onToggle(ToggleEvent e) {
		listToggle.clear();
		listToggle.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
	}

	public PerfilPreConfiguracao getPerfilPreConfiguracaoPage() {
		return loginController.obterPerfilPreConfiguracaoLoginPage(EnumPaginas.ADVOGADO);
	}
	
	public List<Cidade> getCidadesDoEstado(){
		return cidadeServico.obterCidadesDoEstado(this.getCep().getEstado());
	}
	
	public List<Bairro> getBairrosDaCidade(){
		return bairroServico.obterBairrosDaCidade(this.getCep().getCidade());
	}
	
	public List<Logradouro> getLogradourosDoBairro(){
		return logradouroServico.obterLogradourosDoBairro(this.getCep().getBairro());
	}

	public Cep getCep() {
		return cep;
	}

	public void setCep(Cep cep) {
		this.cep = cep;
	}

	public List<br.com.project.modelo.dataTable.Cep> getLazyCepDataTable() {
		return lazyCepDataTable;
	}

	public CepServico getCepServico() {
		return cepServico;
	}

	public void setCepServico(CepServico cepServico) {
		this.cepServico = cepServico;
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

	public List<Cep> getCepsFiltrados() {
		return cepsFiltrados;
	}

	public void setCepsFiltrados(List<Cep> cepsFiltrados) {
		this.cepsFiltrados = cepsFiltrados;
	}

	public List<Boolean> getListToggle() {
		return listToggle;
	}

	public void setListToggle(List<Boolean> listToggle) {
		this.listToggle = listToggle;
	}
}
