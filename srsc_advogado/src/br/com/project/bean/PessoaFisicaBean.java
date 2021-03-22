package br.com.project.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

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
import br.com.project.modelo.Endereco;
import br.com.project.modelo.EnumEstado;
import br.com.project.modelo.EnumPaginas;
import br.com.project.modelo.Login;
import br.com.project.modelo.Logradouro;
import br.com.project.modelo.PerfilPreConfiguracao;
import br.com.project.modelo.PessoaFisica;
import br.com.project.relatorio.jasperIreport.malaDireta.MalaDiretaRelatorio;
import br.com.project.servico.BairroServico;
import br.com.project.servico.CepServico;
import br.com.project.servico.CidadeServico;
import br.com.project.servico.EnderecoServico;
import br.com.project.servico.LogradouroServico;
import br.com.project.servico.PessoaFisicaServico;
import br.com.project.util.Mensagem;

@Controller(value = "pessoaFisicaBean")
@Scope(value = "session")
public class PessoaFisicaBean implements Serializable {

	private static final long serialVersionUID = 2697264978280707957L;

	private PessoaFisica pessoaFisica;
	private List<EnumEstado> estados = Arrays.asList(EnumEstado.values());

//	private List<PessoaFisica> pessoaFisicas = new ArrayList<PessoaFisica>();
	private List<br.com.project.modelo.dataTable.PessoaFisica> lazyPessoaFisicaDataTable;

	@Autowired
	private PessoaFisicaServico pessoaFisicaServico;

	@Autowired
	private EnderecoServico enderecoServico;
//	@Autowired
//	private ViaCepServico viaCepServico;
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

	private List<br.com.project.modelo.dataTable.PessoaFisica> pessoaFisicasFiltrados;
	private List<Boolean> listToggle = Arrays.asList(true, true, true, true, true, true, true, true, true, true, true);

	@PostConstruct
	public void iniciarBean() {
		atualizarListToogleGrid();
		atualizarListaPessoaFisicas();
	}

	public void atualizarListToogleGrid() {
		// listToggle =
		// loginLogado.columnVisibleDataTable(EnumPaginas.PESSOA_FISICA.toString(),
		// 9);
	}

	public void atualizarListaPessoaFisicas() {
//		this.pessoaFisicas = pessoaFisicaServico.listarTodos();
		
		this.lazyPessoaFisicaDataTable = pessoaFisicaServico.getLazyPessoaFisicaDataTable();
	}

	public void novoPessoaFisica() {
		this.pessoaFisica = new PessoaFisica();
		if (this.getPessoaFisica().getEnderecos().size() == 0) {
			this.getPessoaFisica().setEnderecos(new ArrayList<Endereco>());
			this.getPessoaFisica().getEnderecos().add(new Endereco());
			if (this.getPessoaFisica().getEnderecos().get(0).getCep() == null)
				this.getPessoaFisica().getEnderecos().get(0).setCep(new Cep());
		}
		this.resetFormCidade();
		Mensagem.mensagemInformacao(null, null, "Nova Pessoa Física!");
	}

	public void novoPessoaFisicaCancelar() {
		if (this.pessoaFisica.getId() != null) {
			Mensagem.mensagemInformacao(null, null, "A edição do Pessoa Fisica foi cancelada com sucesso!");
		} else
			Mensagem.mensagemInformacao(null, null, "A inclusão do Pessoa Fisica foi cancelada com sucesso!");
		pessoaFisicaNulo();
	}

	public void pessoaFisicaNulo() {
		this.pessoaFisica = null;
	}

	public void salvar() {  
		this.pessoaFisica.setLogin(this.getLoginLogado());
		this.pessoaFisica.setDataCadastro(new Date());
		this.pessoaFisicaServico.salvar(this.pessoaFisica);
		atualizarListaPessoaFisicas();
		pessoaFisicaNulo();
		Mensagem.mensagemInformacao(null, null, "Pessoa Fisica salvo com sucesso!");
	}

	public void editar() {
		if (this.pessoaFisica != null) {
			this.pessoaFisica = pessoaFisicaServico.obterPorId(this.pessoaFisica.getId());
			if (this.getPessoaFisica().getEnderecos().size() == 0) {
				this.getPessoaFisica().setEnderecos(new ArrayList<Endereco>());
				this.getPessoaFisica().getEnderecos().add(new Endereco());
				if (this.getPessoaFisica().getEnderecos().get(0).getCep() == null)
					this.getPessoaFisica().getEnderecos().get(0).setCep(new Cep());
			}
			this.resetFormCidade();
			Mensagem.mensagemInformacao(null, null, "Pessoa Fisica em modo de Edição!");
		}
	}

	public void deletar() {
//		this.pessoaFisica = pessoaFisicaServico.obterPorId(this.pessoaFisica.getId());
		this.enderecoServico.excluirPorPessoa(this.pessoaFisica);
		this.pessoaFisicaServico.excluir(this.pessoaFisica);
		if (this.pessoaFisicasFiltrados != null)
			this.pessoaFisicasFiltrados.remove(this.pessoaFisica);
		this.pessoaFisica = null;
		atualizarListaPessoaFisicas();
		Mensagem.mensagemInformacao(null, null, "Pessoa Fisica foi desativado com sucesso!");
	}

	public Date dataMinima() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -6);
		return calendar.getTime();
	}
	
	public void malaDireta(){
		try {
			if (this.getPessoaFisicasFiltrados() != null)
				new MalaDiretaRelatorio().imprimirPessoaFisica(this.getPessoaFisicasFiltrados(), this.getLoginLogado());
			else 
				new MalaDiretaRelatorio().imprimirPessoaFisica(this.getLazyPessoaFisicaDataTable(), this.getLoginLogado());
		} catch (Exception e) {
			Mensagem.mensagemInformacao(null, null, "NÃ£o foi possivel gerar relatorio Mala Direta!");
		}
	}

	public List<String> getProfissoes() {
		return (List<String>) pessoaFisicaServico.obterProfissoes();
	}

	public List<String> listProfissao(String profissao) {
		return (List<String>) pessoaFisicaServico.obterProfissao(profissao);
	}
	
	public List<Cep> listCeps(String cep){
		return (List<Cep>) cepServico.obterListCepInicia(cep);
	}

	public String getDataAtual() {
		return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
	}

	public void onRowSelect(final SelectEvent event) {
//		this.pessoaFisica = (PessoaFisica) event.getObject();
		br.com.project.modelo.dataTable.PessoaFisica pessoaFisicaDataTable = (br.com.project.modelo.dataTable.PessoaFisica) event.getObject();
		this.pessoaFisica = pessoaFisicaServico.obterPorId(pessoaFisicaDataTable.getId());
	}

	public void onRowDblselect() {
		this.editar();
	}

	public void onVerificaCep(SelectEvent event) {
//		viaCepServico.validaCepServico(this.getEnderecos().get(0).getCep().getCep().replace("-", ""));
		Cep cep = null;
		try {
//			cep = cepServico.obterPorCep(this.getEnderecos().get(0).getCep().getCep());
			cep = cepServico.obterPorId(((Cep) event.getObject()).getId());
		} catch (Exception e) {
			cep = null;
		}
		
		if (cep != null && cep.getCep() != null) {
			this.getPessoaFisica().getEnderecos().get(0).setCep(cep);
		}
		else {
			this.getPessoaFisica().getEnderecos().get(0).getCep().setCep(null);
			this.getPessoaFisica().getEnderecos().get(0).getCep().setEstado(null);
			this.getPessoaFisica().getEnderecos().get(0).getCep().setCidade(null);
			this.getPessoaFisica().getEnderecos().get(0).getCep().setBairro(null);
			this.getPessoaFisica().getEnderecos().get(0).getCep().setLogradouro(null);
			Mensagem.mensagemAlerta("msgCep", null, "Esse cep não está cadastrado!");
		}
	}

	public List<Cidade> getCidadesDoEstado() {
		return cidadeServico.obterCidadesDoEstado(this.getPessoaFisica().getEnderecos().get(0).getCep().getEstado());
	}

	public List<Bairro> getBairrosDaCidade() {
		return bairroServico.obterBairrosDaCidade(this.getPessoaFisica().getEnderecos().get(0).getCep().getCidade());
	}

	public List<Logradouro> getLogradourosDoBairro() {
		return logradouroServico.obterLogradourosDoBairro(this.getPessoaFisica().getEnderecos().get(0).getCep().getBairro());
	}

	public void resetFormCidade() {
		PrimeFaces.current().resetInputs("DialogPessoaFisicaForm");
	}

	public void onToggle(ToggleEvent e) {
		listToggle.clear();
		listToggle.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
	}

	public PerfilPreConfiguracao getPerfilPreConfiguracaoPage() {
		return loginController.obterPerfilPreConfiguracaoLoginPage(EnumPaginas.ADVOGADO);
	}

	public PessoaFisica getPessoaFisica() {
		return pessoaFisica;
	}

	public void setPessoaFisica(PessoaFisica pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
	}

	public List<br.com.project.modelo.dataTable.PessoaFisica> getLazyPessoaFisicaDataTable() {
		return lazyPessoaFisicaDataTable;
	}

	public PessoaFisicaServico getPessoaFisicaServico() {
		return pessoaFisicaServico;
	}

	public void setPessoaFisicaServico(PessoaFisicaServico pessoaFisicaServico) {
		this.pessoaFisicaServico = pessoaFisicaServico;
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

	public List<br.com.project.modelo.dataTable.PessoaFisica> getPessoaFisicasFiltrados() {
		return pessoaFisicasFiltrados;
	}

	public void setPessoaFisicasFiltrados(List<br.com.project.modelo.dataTable.PessoaFisica> pessoaFisicasFiltrados) {
		this.pessoaFisicasFiltrados = pessoaFisicasFiltrados;
	}

	public List<Boolean> getListToggle() {
		return listToggle;
	}

	public void setListToggle(List<Boolean> listToggle) {
		this.listToggle = listToggle;
	}
}
