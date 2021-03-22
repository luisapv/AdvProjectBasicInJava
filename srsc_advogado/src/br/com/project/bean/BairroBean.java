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
import br.com.project.modelo.Login;
import br.com.project.modelo.PerfilPreConfiguracao;
import br.com.project.servico.BairroServico;
import br.com.project.servico.CidadeServico;
import br.com.project.util.Mensagem;

@Controller(value = "bairroBean")
//@ManagedBean("bairroBean")
@Scope(value = "session")
public class BairroBean implements Serializable {

	private static final long serialVersionUID = 2697264978280707957L;

	private Bairro bairro;
//	private List<Bairro> bairros = new ArrayList<Bairro>();
	private List<br.com.project.modelo.dataTable.Bairro> lazyBairroDataTable;

	@Autowired
	private BairroServico bairroServico;
	
	@Autowired
	private CidadeServico cidadeServico;

	@Autowired
	private LoginController loginController;

	private Login loginLogado;

	private List<EnumEstado> estados = Arrays.asList(EnumEstado.values());
	private List<Bairro> bairrosFiltrados;
	private List<Boolean> listToggle = Arrays.asList(true, true, true, true, true);

	public void iniciarBean() {
		atualizarListToogleGrid();
		atualizarListaBairros();
		this.setLoginLogado(loginController.obterLoginLogado());
	}

	public void atualizarListToogleGrid() {
		// listToggle = loginLogado.columnVisibleDataTable(EnumPaginas.ADVOGADO.toString(), 5);
	}

	public void atualizarListaBairros() {
//		this.bairros = bairroServico.listarTodos();
		this.lazyBairroDataTable = bairroServico.getLazyBairroDataTable();
	}

	public void novoBairro() {
		this.bairro = new Bairro();
		this.resetFormCidade();
		Mensagem.mensagemInformacao(null, null, "Novo Bairro!");
	}

	public void novoBairroCancelar() {
		if (this.bairro.getId() != null) {
			Mensagem.mensagemInformacao(null, null, "A edição do Bairro foi cancelada com sucesso!");
		} else
			Mensagem.mensagemInformacao(null, null, "A inclusão do Bairro foi cancelada com sucesso!");
		bairroNulo();
	}

	public void bairroNulo() {
		this.bairro = null;
	}

	public void salvar() {
		this.bairro.setDataCadastro(new Date());
		this.bairro.setLogin(loginLogado);
		this.bairroServico.salvar(this.bairro);
		atualizarListaBairros();
		bairroNulo();
		Mensagem.mensagemInformacao(null, null, "Bairro salvo com sucesso!");
	}

	public void editar() {
		if (this.bairro != null) {
//			this.bairro = bairroServico.obterPorId(this.bairro.getId());
			this.resetFormCidade();
			Mensagem.mensagemInformacao(null, null, "Bairro em modo de Edição!");
		}
	}

	public void deletar() {
//		this.bairro = bairroServico.obterPorId(this.bairro.getId());
		this.bairroServico.excluir(this.bairro);
		if (this.bairrosFiltrados != null)
			this.bairrosFiltrados.remove(this.bairro);
		this.bairro = null;
		atualizarListaBairros();
		Mensagem.mensagemInformacao(null, null, "Bairro foi desativado com sucesso!");
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
		br.com.project.modelo.dataTable.Bairro bairroDataTable = (br.com.project.modelo.dataTable.Bairro) event.getObject();
		this.bairro = bairroServico.obterPorId(bairroDataTable.getId());
	}

	public void onRowDblselect() {
//		this.bairro = (Bairro) event.getObject();
		this.editar();
	}

	public void resetFormCidade() {
		PrimeFaces.current().resetInputs("DialogBairroForm");
	}

	public void onToggle(ToggleEvent e) {
		listToggle.clear();
		listToggle.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
	}

	public List<Cidade> getCidadesDoEstado(){
		return cidadeServico.obterCidadesDoEstado(this.bairro.getCidade().getEstado());
	}

	public PerfilPreConfiguracao getPerfilPreConfiguracaoPage() {
		return loginController.obterPerfilPreConfiguracaoLoginPage(EnumPaginas.ADVOGADO);
	}

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public List<br.com.project.modelo.dataTable.Bairro> getLazyBairroDataTable() {
		return lazyBairroDataTable;
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

	public List<Bairro> getBairrosFiltrados() {
		return bairrosFiltrados;
	}

	public void setBairrosFiltrados(List<Bairro> bairrosFiltrados) {
		this.bairrosFiltrados = bairrosFiltrados;
	}

	public List<Boolean> getListToggle() {
		return listToggle;
	}

	public void setListToggle(List<Boolean> listToggle) {
		this.listToggle = listToggle;
	}
	
}
