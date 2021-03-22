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
import br.com.project.modelo.Advogado;
import br.com.project.modelo.EnumEstado;
import br.com.project.modelo.EnumPaginas;
import br.com.project.modelo.Login;
import br.com.project.modelo.PerfilPreConfiguracao;
import br.com.project.servico.AdvogadoServico;
import br.com.project.util.Mensagem;

@Controller(value = "advogadoBean")
@Scope(value = "session")
public class AdvogadoBean implements Serializable {

	private static final long serialVersionUID = 2697264978280707957L;

	private Advogado advogado;
//	private List<Advogado> advogados = new ArrayList<Advogado>();
	private List<br.com.project.modelo.dataTable.Advogado> lazyAdvogadoDataTable;

	@Autowired
	private AdvogadoServico advogadoServico;
	
	@Autowired
	private LoginController loginController;
	
	private Login loginLogado;

	private List<EnumEstado> estados = Arrays.asList(EnumEstado.values());
	private List<Advogado> advogadosFiltrados;
	private List<Boolean> listToggle = Arrays.asList(true, true, true, true, true);

	public void iniciarBean() {
//		atualizarListToogleGrid();
		atualizarListaAdvogados();
		this.loginLogado = loginController.obterLoginLogado();
	}

	public void atualizarListToogleGrid() {
		listToggle = loginLogado.columnVisibleDataTable(EnumPaginas.ADVOGADO.toString(), 5);
	}

	public void atualizarListaAdvogados() {
//		this.advogados = advogadoServico.listarTodos();
		this.lazyAdvogadoDataTable = advogadoServico.getLazyAdvogadoDataTable();
	}

	public void novoAdvogado() {
		this.advogado = new Advogado();
		this.resetFormCidade();
		Mensagem.mensagemInformacao(null, null, "Novo Advogado!");
	}

	public void novoAdvogadoCancelar() {
		if (this.advogado.getId() != null) {
			Mensagem.mensagemInformacao(null, null, "A edição do Advogado foi cancelada com sucesso!");
		} else
			Mensagem.mensagemInformacao(null, null, "A inclusão do Advogado foi cancelada com sucesso!");
		this.advogado = null;
	}

	public void advogadoNulo() {
		this.advogado = null;
	}

	public void salvar() {
		if ((this.advogado.getNome()==null || this.advogado.getNome().isEmpty()) || (this.advogado.getIdentidade()==null || this.advogado.getIdentidade().isEmpty()) ||
			(this.advogado.getEstadoIdentidade()==null)){
			if(this.advogado.getNome()==null || this.advogado.getNome().isEmpty()){
				Mensagem.mensagemAlerta(null, null, "O campo nome deve ser preenchido!");
			}
			if(this.advogado.getIdentidade()==null || this.advogado.getIdentidade().isEmpty()){
				Mensagem.mensagemAlerta(null, null, "O campo oab deve ser preenchido!");
			}
			if(this.advogado.getEstadoIdentidade()==null){
				Mensagem.mensagemAlerta(null, null, "O campo estado deve ser preenchido/selecionado!");
			}
		}
		else{
//			if (this.advogado.getId()==null){
//				this.advogado.setProfissao("ADVOGADO");
//				this.advogado.setDocumento(this.advogado.getIdentidade()+"/"+this.advogado.getEstadoIdentidade());
//			}
			this.advogado.setLogin(this.loginLogado);
			this.advogado.setDataCadastro(new Date());
			this.advogadoServico.salvar(this.advogado);
			atualizarListaAdvogados();
			this.advogado = null;
			Mensagem.mensagemInformacao(null, null, "Advogado salvo com sucesso!");
		}
	}

	public void editar() {
		if (this.advogado != null) {
//			this.advogado = advogadoServico.obterPorId(this.advogado.getId());
			this.resetFormCidade();
			Mensagem.mensagemInformacao(null, null, "Advogado em modo de Edição!");
		}
	}

	public void deletar() {
//		this.advogado = advogadoServico.obterPorId(this.advogado.getId());
		this.advogadoServico.excluir(this.advogado);
		if (this.advogadosFiltrados != null)
			this.advogadosFiltrados.remove(this.advogado);
		this.advogado = null;
		atualizarListaAdvogados();
		Mensagem.mensagemInformacao(null, null, "Advogado foi desativado com sucesso!");
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
		br.com.project.modelo.dataTable.Advogado advogadoDataTable = (br.com.project.modelo.dataTable.Advogado) event.getObject();
		this.advogado = advogadoServico.obterPorId(advogadoDataTable.getId());
	}

	public void onRowDblselect() {
//		this.advogado = (Advogado) event.getObject();
		this.editar();
	}

	public void resetFormCidade() {
		PrimeFaces.current().resetInputs("DialogAdvogadoForm");
	}

	public void onToggle(ToggleEvent e) {
		listToggle.clear();
		listToggle.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
	}

	public PerfilPreConfiguracao getPerfilPreConfiguracaoPage() {
		return loginController.obterPerfilPreConfiguracaoLoginPage(EnumPaginas.ADVOGADO);
	}

	public Advogado getAdvogado() {
		return advogado;
	}

	public void setAdvogado(Advogado advogado) {
		this.advogado = advogado;
	}

	public List<br.com.project.modelo.dataTable.Advogado> getLazyAdvogadoDataTable() {
		return lazyAdvogadoDataTable;
	}

	public AdvogadoServico getAdvogadoServico() {
		return advogadoServico;
	}

	public void setAdvogadoServico(AdvogadoServico advogadoServico) {
		this.advogadoServico = advogadoServico;
	}

	public Login getLoginLogado() {
		return loginLogado;
	}

	public void setLoginLogado(Login loginLogado) {
		this.loginLogado = loginLogado;
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

	public List<Advogado> getAdvogadosFiltrados() {
		return advogadosFiltrados;
	}

	public void setAdvogadosFiltrados(List<Advogado> advogadosFiltrados) {
		this.advogadosFiltrados = advogadosFiltrados;
	}

	public List<Boolean> getListToggle() {
		return listToggle;
	}

	public void setListToggle(List<Boolean> listToggle) {
		this.listToggle = listToggle;
	}
}
