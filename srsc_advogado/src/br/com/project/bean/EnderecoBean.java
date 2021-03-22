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
import br.com.project.modelo.Endereco;
import br.com.project.modelo.EnumEstado;
import br.com.project.modelo.EnumPaginas;
import br.com.project.modelo.Login;
import br.com.project.modelo.PerfilPreConfiguracao;
import br.com.project.relatorio.jasperIreport.malaDireta.MalaDiretaRelatorio;
import br.com.project.servico.EnderecoServico;
import br.com.project.util.Mensagem;

@Controller(value = "enderecoBean")
@Scope(value = "session")
public class EnderecoBean implements Serializable {

	private static final long serialVersionUID = 2697264978280707957L;

	private Endereco endereco;
//	private List<Endereco> enderecos = new ArrayList<Endereco>();
	private List<br.com.project.modelo.dataTable.Endereco> lazyEnderecoDataTable;

	@Autowired
	private EnderecoServico enderecoServico;

	@Autowired
	private LoginController loginController;

	private Login loginLogado;

	private List<EnumEstado> estados = Arrays.asList(EnumEstado.values());
	private List<br.com.project.modelo.dataTable.Endereco> enderecosFiltrados;
	private List<Boolean> listToggle = Arrays.asList(true, true, true, true, true, true, true);

	public void iniciarBean() {
		atualizarListToogleGrid();
		atualizarListaEnderecos();
		this.setLoginLogado(loginController.obterLoginLogado());
	}

	public void atualizarListToogleGrid() {
		// listToggle = loginLogado.columnVisibleDataTable(EnumPaginas.ADVOGADO.toString(), 5);
	}

	public void atualizarListaEnderecos() {
//		this.enderecos = enderecoServico.listarTodos();
		
		this.lazyEnderecoDataTable = enderecoServico.getLazyEnderecoDataTable();
	}

	public void novoEndereco() {
		this.endereco = new Endereco();
		this.resetFormCidade();
		Mensagem.mensagemInformacao(null, null, "Novo Endereco!");
	}

	public void novoEnderecoCancelar() {
		if (this.endereco.getId() != null) {
			Mensagem.mensagemInformacao(null, null, "A edição do Endereco foi cancelada com sucesso!");
		} else
			Mensagem.mensagemInformacao(null, null, "A inclusão do Endereco foi cancelada com sucesso!");
		this.endereco = null;
	}

	public void enderecoNulo() {
		this.endereco = null;
	}

	public void salvar() {
		this.endereco.setDataCadastro(new Date());
		this.endereco.setLogin(loginLogado);
		this.enderecoServico.salvar(this.endereco);
		atualizarListaEnderecos();
		this.endereco = null;
		Mensagem.mensagemInformacao(null, null, "Endereco salvo com sucesso!");
	}

	public void editar() {
		if (this.endereco != null) {
			this.endereco = enderecoServico.obterPorId(this.endereco.getId());
			this.resetFormCidade();
			Mensagem.mensagemInformacao(null, null, "Endereco em modo de Edição!");
		}
	}

	public void deletar() {
		this.endereco = enderecoServico.obterPorId(this.endereco.getId());
		this.enderecoServico.excluir(this.endereco);
		if (this.enderecosFiltrados != null)
			this.enderecosFiltrados.remove(this.endereco);
		this.endereco = null;
		atualizarListaEnderecos();
		Mensagem.mensagemInformacao(null, null, "Endereco foi desativado com sucesso!");
	}

	public Date dataMinima() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -6);
		return calendar.getTime();
	}
	
	public void malaDireta(){
		try {
			if (this.getEnderecosFiltrados() != null)
				new MalaDiretaRelatorio().imprimirEndereco(this.getEnderecosFiltrados(), this.getLoginLogado());
			else
				new MalaDiretaRelatorio().imprimirEndereco(this.getLazyEnderecoDataTable(), this.getLoginLogado());
		} catch (Exception e) {
			Mensagem.mensagemErro(null, null, "Não foi possivel gerar relatorio Mala Direta!");
		}
	}

	public String getDataAtual() {
		return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
	}

	public void onRowDblselect(final SelectEvent event) {
		this.endereco = (Endereco) event.getObject();
		this.editar();
	}

	public void resetFormCidade() {
		PrimeFaces.current().resetInputs("DialogEnderecoForm");
	}

	public void onToggle(ToggleEvent e) {
		listToggle.clear();
		listToggle.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
	}

	public PerfilPreConfiguracao getPerfilPreConfiguracaoPage() {
		return loginController.obterPerfilPreConfiguracaoLoginPage(EnumPaginas.ADVOGADO);
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<br.com.project.modelo.dataTable.Endereco> getLazyEnderecoDataTable() {
		return lazyEnderecoDataTable;
	}

	public EnderecoServico getEnderecoServico() {
		return enderecoServico;
	}

	public void setEnderecoServico(EnderecoServico enderecoServico) {
		this.enderecoServico = enderecoServico;
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

	public List<br.com.project.modelo.dataTable.Endereco> getEnderecosFiltrados() {
		return enderecosFiltrados;
	}

	public void setEnderecosFiltrados(List<br.com.project.modelo.dataTable.Endereco> enderecosFiltrados) {
		this.enderecosFiltrados = enderecosFiltrados;
	}

	public List<Boolean> getListToggle() {
		return listToggle;
	}

	public void setListToggle(List<Boolean> listToggle) {
		this.listToggle = listToggle;
	}
}
