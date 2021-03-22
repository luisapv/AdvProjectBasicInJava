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
import br.com.project.modelo.Bairro;
import br.com.project.modelo.Cep;
import br.com.project.modelo.Cidade;
import br.com.project.modelo.Endereco;
import br.com.project.modelo.EnumEstado;
import br.com.project.modelo.EnumPaginas;
import br.com.project.modelo.Login;
import br.com.project.modelo.Logradouro;
import br.com.project.modelo.PerfilPreConfiguracao;
import br.com.project.modelo.PessoaJuridica;
import br.com.project.relatorio.jasperIreport.malaDireta.MalaDiretaRelatorio;
import br.com.project.servico.BairroServico;
import br.com.project.servico.CepServico;
import br.com.project.servico.CidadeServico;
import br.com.project.servico.EnderecoServico;
import br.com.project.servico.LogradouroServico;
import br.com.project.servico.PessoaJuridicaServico;
import br.com.project.util.Mensagem;

@Controller(value = "pessoaJuridicaBean")
@Scope(value = "session")
public class PessoaJuridicaBean implements Serializable {

	private static final long serialVersionUID = 2697264978280707957L;

	private PessoaJuridica pessoaJuridica;
	private List<EnumEstado> estados = Arrays.asList(EnumEstado.values());

//	private List<PessoaJuridica> pessoaJuridicas = new ArrayList<PessoaJuridica>();
	private List<br.com.project.modelo.dataTable.PessoaJuridica> lazyPessoaJuridicaDataTable;

	@Autowired
	private PessoaJuridicaServico pessoaJuridicaServico;

	@Autowired
	private EnderecoServico enderecoServico;
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

	private List<br.com.project.modelo.dataTable.PessoaJuridica> pessoaJuridicasFiltrados;
	private List<Boolean> listToggle = Arrays.asList(true, true, true, true, true, true, true);

	public void iniciarBean() {
		atualizarListToogleGrid();
		atualizarListaPessoaJuridicas();
	}

	public void atualizarListToogleGrid() {
		// listToggle =
		// loginLogado.columnVisibleDataTable(EnumPaginas.PESSOA_FISICA.toString(),
		// 9);
	}

	public void atualizarListaPessoaJuridicas() {
//		this.pessoaJuridicas = pessoaJuridicaServico.listarTodos();
		
		this.lazyPessoaJuridicaDataTable = pessoaJuridicaServico.getLazyPessoaJuridicaDataTable();
	}

	public void novoPessoaJuridica() {
		this.pessoaJuridica = new PessoaJuridica();
		if (this.getPessoaJuridica().getEnderecos().size() == 0) {
			this.getPessoaJuridica().setEnderecos(new ArrayList<Endereco>());
			this.getPessoaJuridica().getEnderecos().add(new Endereco());
			if (this.getPessoaJuridica().getEnderecos().get(0).getCep() == null)
				this.getPessoaJuridica().getEnderecos().get(0).setCep(new Cep());
		}
		this.resetFormCidade();
		Mensagem.mensagemInformacao(null, null, "Novo PessoaJuridica!");
	}

	public void novoPessoaJuridicaCancelar() {
		if (this.pessoaJuridica.getId() != null) {
			Mensagem.mensagemInformacao(null, null, "A edição do Pessoa Juridica foi cancelada com sucesso!");
		} else
			Mensagem.mensagemInformacao(null, null, "A inclusão do Pessoa Juridica foi cancelada com sucesso!");
		pessoaJuridicaNulo();
	}

	public void pessoaJuridicaNulo() {
		this.pessoaJuridica = null;
	}

	public void salvar() {
		this.pessoaJuridica.setLogin(this.getLoginLogado());
		this.pessoaJuridica.setDataCadastro(new Date());
		this.pessoaJuridicaServico.salvar(this.pessoaJuridica);
		atualizarListaPessoaJuridicas();
		pessoaJuridicaNulo();
		Mensagem.mensagemInformacao(null, null, "Pessoa Juridica salvo com sucesso!");
	}

	public void editar() {
		if (this.pessoaJuridica != null) {
			this.pessoaJuridica = pessoaJuridicaServico.obterPorId(this.pessoaJuridica.getId());
			if (this.getPessoaJuridica().getEnderecos().size() == 0) {
				this.getPessoaJuridica().setEnderecos(new ArrayList<Endereco>());
				this.getPessoaJuridica().getEnderecos().add(new Endereco());
				if (this.getPessoaJuridica().getEnderecos().get(0).getCep() == null)
					this.getPessoaJuridica().getEnderecos().get(0).setCep(new Cep());
			}
			this.resetFormCidade();
			Mensagem.mensagemInformacao(null, null, "Pessoa Fisica em modo de Edição!");
		}
	}

	public void deletar() {
//		this.pessoaJuridica = pessoaJuridicaServico.obterPorId(this.pessoaJuridica.getId());
		this.enderecoServico.excluirPorPessoa(this.pessoaJuridica);
		this.pessoaJuridicaServico.excluir(this.pessoaJuridica);
		if (this.pessoaJuridicasFiltrados != null)
			this.pessoaJuridicasFiltrados.remove(this.pessoaJuridica);
		this.pessoaJuridica = null;
		atualizarListaPessoaJuridicas();
		Mensagem.mensagemErro(null, null, "Pessoa Juridica foi desativado com sucesso!");
	}

	public Date dataMinima() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -6);
		return calendar.getTime();
	}
	
	public void malaDireta(){
		try {
			if (this.getPessoaJuridicasFiltrados() != null)
			new MalaDiretaRelatorio().imprimirPessoaJuridica(this.getPessoaJuridicasFiltrados(), this.getLoginLogado());
			else
				new MalaDiretaRelatorio().imprimirPessoaJuridica(this.getLazyPessoaJuridicaDataTable(), this.getLoginLogado());
		} catch (Exception e) {
			Mensagem.mensagemErro(null, null, "NÃ£o foi possivel gerar relatorio Mala Direta!");
		}
	}
	
	public List<Cep> listCeps(String cep){
		return (List<Cep>) cepServico.obterListCepInicia(cep);
	}

	public String getDataAtual() {
		return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
	}

	public void onRowSelect(final SelectEvent event) {
		br.com.project.modelo.dataTable.PessoaJuridica pessoaJuridicaDataTable = (br.com.project.modelo.dataTable.PessoaJuridica) event.getObject(); 
		this.pessoaJuridica = pessoaJuridicaServico.obterPorId(pessoaJuridicaDataTable.getId());
	}

	public void onRowDblselect() {
		this.editar();
	}

	public void onVerificaCep(SelectEvent event) {
//		viaCepServico.validaCepServico(this.getPessoaJuridica().getEnderecos().get(0).getCep().getCep().replace("-", ""));
		Cep cep = null;
		try {
//			cep = cepServico.obterPorCep(this.getPessoaJuridica().getEnderecos().get(0).getCep().getCep());
			cep = cepServico.obterPorId(((Cep) event.getObject()).getId());
		} catch (Exception e) {
			cep = null;
		}
		
		if (cep != null && cep.getCep() != null) {
			this.getPessoaJuridica().getEnderecos().get(0).setCep(cep);
		}
		else {
			this.getPessoaJuridica().getEnderecos().get(0).getCep().setCep(null);
			this.getPessoaJuridica().getEnderecos().get(0).getCep().setEstado(null);
			this.getPessoaJuridica().getEnderecos().get(0).getCep().setCidade(null);
			this.getPessoaJuridica().getEnderecos().get(0).getCep().setBairro(null);
			this.getPessoaJuridica().getEnderecos().get(0).getCep().setLogradouro(null);
			Mensagem.mensagemAlerta("msgCep", null, "Esse cep nÃ£o estÃ¡ cadastrado!");
		}
	}

	public List<Cidade> getCidadesDoEstado() {
		return cidadeServico.obterCidadesDoEstado(this.getPessoaJuridica().getEnderecos().get(0).getCep().getEstado());
	}

	public List<Bairro> getBairrosDaCidade() {
		return bairroServico.obterBairrosDaCidade(this.getPessoaJuridica().getEnderecos().get(0).getCep().getCidade());
	}

	public List<Logradouro> getLogradourosDoBairro() {
		return logradouroServico.obterLogradourosDoBairro(this.getPessoaJuridica().getEnderecos().get(0).getCep().getBairro());
	}

	public void resetFormCidade() {
		PrimeFaces.current().resetInputs("DialogPessoaJuridicaForm");
	}

	public void onToggle(ToggleEvent e) {
		listToggle.clear();
		listToggle.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
	}

	@SuppressWarnings("null")
	public String getEnderecoCompleto() {
		this.getPessoaJuridica().setEnderecos(enderecoServico.obterPorPessoa(this.pessoaJuridica));
		StringBuilder enderecoCompleto = null;
		if (this.getPessoaJuridica().getEnderecos().get(0).getCep() != null) {
			enderecoCompleto.append(this.getPessoaJuridica().getEnderecos().get(0).getCep().getLogradouro().getLogradouroCompleto());
			enderecoCompleto.append(", ");
			if (this.getPessoaJuridica().getEnderecos().get(0).getNumero() != "" || this.getPessoaJuridica().getEnderecos().get(0).getNumero() != null) {
				enderecoCompleto.append(this.getPessoaJuridica().getEnderecos().get(0).getNumero());
				if (this.getPessoaJuridica().getEnderecos().get(0).getComplemento() != ""
						|| this.getPessoaJuridica().getEnderecos().get(0).getComplemento() != null) {
					enderecoCompleto.append(" - ");
					enderecoCompleto.append(this.getPessoaJuridica().getEnderecos().get(0).getComplemento());
				}
				enderecoCompleto.append(", ");
			} else if (this.getPessoaJuridica().getEnderecos().get(0).getComplemento() != ""
					|| this.getPessoaJuridica().getEnderecos().get(0).getComplemento() != null) {
				enderecoCompleto.append(this.getPessoaJuridica().getEnderecos().get(0).getComplemento());
				enderecoCompleto.append(", ");
			}
			enderecoCompleto.append(this.getPessoaJuridica().getEnderecos().get(0).getCep().getBairro().getNome());
			enderecoCompleto.append(", ");
			enderecoCompleto.append(this.getPessoaJuridica().getEnderecos().get(0).getCep().getCidade().getNome());
			enderecoCompleto.append("/");
			enderecoCompleto.append(this.getPessoaJuridica().getEnderecos().get(0).getCep().getEstado());
			enderecoCompleto.append(" - ");
			enderecoCompleto.append(this.getPessoaJuridica().getEnderecos().get(0).getCep().getCep());
		}
		if (enderecoCompleto == null)
			enderecoCompleto.append(" ");
		return enderecoCompleto.toString();
	}

	public PerfilPreConfiguracao getPerfilPreConfiguracaoPage() {
		return loginController.obterPerfilPreConfiguracaoLoginPage(EnumPaginas.ADVOGADO);
	}

	public PessoaJuridica getPessoaJuridica() {
		return pessoaJuridica;
	}

	public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
		this.pessoaJuridica = pessoaJuridica;
	}

	public List<br.com.project.modelo.dataTable.PessoaJuridica> getLazyPessoaJuridicaDataTable() {
		return lazyPessoaJuridicaDataTable;
	}

	public PessoaJuridicaServico getPessoaJuridicaServico() {
		return pessoaJuridicaServico;
	}

	public void setPessoaJuridicaServico(PessoaJuridicaServico pessoaJuridicaServico) {
		this.pessoaJuridicaServico = pessoaJuridicaServico;
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

	public List<br.com.project.modelo.dataTable.PessoaJuridica> getPessoaJuridicasFiltrados() {
		return pessoaJuridicasFiltrados;
	}

	public void setPessoaJuridicasFiltrados(List<br.com.project.modelo.dataTable.PessoaJuridica> pessoaJuridicasFiltrados) {
		this.pessoaJuridicasFiltrados = pessoaJuridicasFiltrados;
	}

	public List<Boolean> getListToggle() {
		return listToggle;
	}

	public void setListToggle(List<Boolean> listToggle) {
		this.listToggle = listToggle;
	}
}
