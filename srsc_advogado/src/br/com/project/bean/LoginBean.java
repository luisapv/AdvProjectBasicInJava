package br.com.project.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;

import br.com.project.control.LoginController;
import br.com.project.dao.CidadeDAO;
import br.com.project.dao.popula.PopulaCidade;
import br.com.project.modelo.Bairro;
import br.com.project.modelo.Cep;
import br.com.project.modelo.Cidade;
import br.com.project.modelo.Endereco;
import br.com.project.modelo.EnumPaginas;
import br.com.project.modelo.EnumTema;
import br.com.project.modelo.Login;
import br.com.project.modelo.Logradouro;
import br.com.project.servico.BairroServico;
import br.com.project.servico.CepServico;
import br.com.project.servico.CidadeServico;
import br.com.project.servico.LoginServico;
import br.com.project.servico.LogradouroServico;
import br.com.project.util.Mensagem;

@SuppressWarnings({ "deprecation", "unused" })
@Controller(value = "loginBean")
@Scope(value = "session")
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 7210312072110293463L;

	@Autowired
	private LoginController loginController;
	@Autowired
	private LoginServico loginServico;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CepServico cepServico;
	@Autowired
	private CidadeServico cidadeServico;
	@Autowired
	private BairroServico bairroServico;
	@Autowired
	private LogradouroServico logradouroServico;

	private Login loginLogado;

	private Login login;

	private List<EnumTema> temas = Arrays.asList(EnumTema.values());

	// Atributos
	private String senhaAntiga;
	private String senhaNova;
	private String senhaConfirmacao;

	// Controles
	private List<br.com.project.modelo.dataTable.Login> lazyLoginDataTable;

	private List<br.com.project.modelo.dataTable.Login> loginsFiltrados;

	private List<Boolean> listToggle = Arrays.asList(true, true, true, true, true, true, true, true);

	public void iniciarBean() {
		// atualizarListToogleGrid();
		atualizarListaLogins();
		this.loginLogado = loginController.obterLoginLogado();
	}

	public void atualizarListToogleGrid() {
		listToggle = loginLogado.columnVisibleDataTable(EnumPaginas.LOGIN.toString(), 8);
	}

	public void atualizarListaLogins() {
		// this.logins = loginServico.listarTodos();
		this.lazyLoginDataTable = loginServico.getLazyLoginDataTable();
	}

	public void resetFormLogin() {
		PrimeFaces.current().resetInputs("DialogLoginForm");
	}

	public void novoLogin() {
		this.login = new Login();
		if (this.login.getEndereco() == null)
			this.login.setEndereco(new Endereco());
		this.resetFormLogin();
		Mensagem.mensagemInformacao(null, null, "Novo Login!");
	}

	public void salvarPerfil() {
		this.loginLogado.setDataCadastro(new Date());
		this.loginServico.salvar(this.loginLogado);
		atualizarListaLogins();
		Mensagem.mensagemInformacao(null, null, "Perfil salvo com sucesso!");
	}

	public void alterarPerfil(){
//		this.login = loginServico.obterPorId(this.getLoginLogado().getId());
		if (this.getLoginLogado().getEndereco() == null)
			this.getLoginLogado().setEndereco(new Endereco());
	}
	
	public void cancelarPerfil(){
		this.loginLogado = loginServico.obterPorId(this.loginLogado.getId());
	}
	
	public void novoLoginCancelar() {
		if (this.login.getId() != null) {
			Mensagem.mensagemInformacao(null, null, "A edição do Login foi cancelada com sucesso!");
		} else
			Mensagem.mensagemInformacao(null, null, "A inclusão do Login foi cancelada com sucesso!");
		this.login = null;
	}

	public void salvar() {
		if (this.login.getNome() == null || this.login.getNome().isEmpty()) {
			if (this.login.getNome() == null || this.login.getNome().isEmpty()) {
				Mensagem.mensagemAlerta(null, null, "O campo nome deve ser preenchido!");
			}
		} else {
			// if (this.login.getId()==null){
			// this.login.setProfissao("ADVOGADO");
			// this.login.setDocumento(this.login.getIdentidade()+"/"+this.login.getEstadoIdentidade());
			// }
			this.login.setDataCadastro(new Date());
			this.loginServico.salvar(this.login);
			atualizarListaLogins();
			this.login = null;
			Mensagem.mensagemInformacao(null, null, "Login salvo com sucesso!");
		}
	}

	public void editar() {
		if (this.login != null) {
			// this.login = loginServico.obterPorId(this.login.getId());
			this.resetFormLogin();
			Mensagem.mensagemInformacao(null, null, "Login em modo de Edição!");
		}
	}

	public void deletar() {
		// this.login = loginServico.obterPorId(this.login.getId());
		this.loginServico.excluir(this.login);
		if (this.loginsFiltrados != null)
			this.loginsFiltrados.remove(this.login);
		this.login = null;
		atualizarListaLogins();
		Mensagem.mensagemInformacao(null, null, "Login foi desativado com sucesso!");
	}

	private String criptoSenha(String senha) {
		return passwordEncoder.encodePassword(senha, null);
	}

	public void alterarSenha() {
		if (criptoSenha(senhaAntiga).equals(loginLogado.getSenha())) {
			if (senhaNova.equals(senhaConfirmacao)) {
				loginLogado.setSenha(criptoSenha(senhaNova));
				loginLogado = loginServico.salvarReturn(loginLogado);
				Mensagem.mensagemInformacao("msgAlterarSenha", "Alterado com sucesso!", null);
			} else {
				Mensagem.mensagemInformacao("msgAlterarSenha", "Senha e Confirmação devem ser iguais!", null);
			}
		} else {
			Mensagem.mensagemInformacao("msgAlterarSenha", "Senha antiga errada!", null);
		}
		cancelarAlteracaoSenha();
	}

	public void onRowSelect(final SelectEvent event) {
		br.com.project.modelo.dataTable.Login loginDataTable = (br.com.project.modelo.dataTable.Login) event
				.getObject();
		this.login = loginServico.obterPorId(loginDataTable.getId());
		if (this.login.getEndereco() == null)
			this.login.setEndereco(new Endereco());
	}

	public void onRowDblselect() {
		// this.login = (Login) event.getObject();
		this.editar();
	}

	public void cancelarAlteracaoSenha() {
		this.senhaAntiga = null;
		this.senhaNova = null;
		this.senhaConfirmacao = null;
	}

	public void onVerificaCep() {
		// viaCepServico.validaCepServico(this.getEnderecos().get(0).getCep().getCep().replace("-",
		// ""));
		Cep cep = null;
		if (this.login != null) {
			try {
				cep = (Cep) new GeralBean().getCep(this.login.getEndereco().getCep().getCep());
			} catch (Exception e) {
				cep = null;
			}

			if (cep != null) {
				this.login.getEndereco().setCep(cep);
			} else {
				this.login.getEndereco().getCep().setCep(null);
				this.login.getEndereco().getCep().setEstado(null);
				this.login.getEndereco().getCep().setCidade(null);
				this.login.getEndereco().getCep().setBairro(null);
				this.login.getEndereco().getCep().setLogradouro(null);
				Mensagem.mensagemAlerta("msgCep", null, "Esse cep não está cadastrado!");
			}
		} else {
			try {
				cep = (Cep) new GeralBean().getCep(this.loginLogado.getEndereco().getCep().getCep());
			} catch (Exception e) {
				cep = null;
			}

			if (cep != null) {
				this.loginLogado.getEndereco().setCep(cep);
			} else {
				this.loginLogado.getEndereco().getCep().setCep(null);
				this.loginLogado.getEndereco().getCep().setEstado(null);
				this.loginLogado.getEndereco().getCep().setCidade(null);
				this.loginLogado.getEndereco().getCep().setBairro(null);
				this.loginLogado.getEndereco().getCep().setLogradouro(null);
				Mensagem.mensagemAlerta("msgCep", null, "Esse cep não está cadastrado!");
			}
		}
	}

	public List<Cidade> getCidadesDoEstado() {
		if (this.login != null)
			return cidadeServico.obterCidadesDoEstado(this.login.getEndereco().getCep().getEstado());
		else
			return cidadeServico.obterCidadesDoEstado(this.loginLogado.getEndereco().getCep().getEstado());
	}

	public List<Bairro> getBairrosDaCidade() {
		if (this.login != null)
			return bairroServico.obterBairrosDaCidade(this.login.getEndereco().getCep().getCidade());
		else
			return bairroServico.obterBairrosDaCidade(this.loginLogado.getEndereco().getCep().getCidade());
	}

	public List<Logradouro> getLogradourosDoBairro() {
		if (this.login != null)
			return logradouroServico.obterLogradourosDoBairro(this.login.getEndereco().getCep().getBairro());
		else
			return logradouroServico.obterLogradourosDoBairro(this.loginLogado.getEndereco().getCep().getBairro());
	}

	// public List<Cidade> getCidadesDoEstado() {
	// EnumEstado estado = null;
	// if (this.login != null)
	// estado = this.login.getEndereco().getCep().getEstado();
	// else if (this.loginLogado.getEndereco().getCep().getEstado() != null)
	// estado = this.loginLogado.getEndereco().getCep().getEstado();
	// return new GeralBean().getCidadesDoEstado(estado);
	// }
	//
	// public List<Bairro> getBairrosDaCidade() {
	// Cidade cidade = null;
	// if (this.login != null)
	// cidade = this.login.getEndereco().getCep().getCidade();
	// else if (this.loginLogado.getEndereco().getCep().getEstado() != null)
	// cidade = this.loginLogado.getEndereco().getCep().getCidade();
	// return new GeralBean().getBairrosDaCidade(cidade);
	// }
	//
	// public List<Logradouro> getLogradourosDoBairro() {
	// Bairro bairro = null;
	// if (this.login != null)
	// bairro = this.login.getEndereco().getCep().getBairro();
	// else if (this.loginLogado.getEndereco().getCep().getEstado() != null)
	// bairro = this.loginLogado.getEndereco().getCep().getBairro();
	// return new GeralBean().getLogradourosDoBairro(bairro);
	// }

	public LoginController getLoginController() {
		return loginController;
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public Login getLoginLogado() {
		setLoginLogado();
		if (loginLogado == null) {
			loginLogado = new Login();
		}
		return loginLogado;
	}

	public void setLoginLogado() {
		this.loginLogado = loginController.obterLoginLogado();
	}

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public List<EnumTema> getTemas() {
		return temas;
	}

	public void setTemas(List<EnumTema> temas) {
		this.temas = temas;
	}

	public void atualizaTema() {
		loginLogado = loginServico.salvarReturn(loginLogado);
	}

	public List<br.com.project.modelo.dataTable.Login> getLoginsFiltrados() {
		return loginsFiltrados;
	}

	public void setLoginsFiltrados(List<br.com.project.modelo.dataTable.Login> loginsFiltrados) {
		this.loginsFiltrados = loginsFiltrados;
	}

	public List<Boolean> getListToggle() {
		return listToggle;
	}

	public void setListToggle(List<Boolean> listToggle) {
		this.listToggle = listToggle;
	}

	public List<br.com.project.modelo.dataTable.Login> getLazyLoginDataTable() {
		return lazyLoginDataTable;
	}

	public String getSenhaAntiga() {
		return senhaAntiga;
	}

	public void setSenhaAntiga(String senhaAntiga) {
		this.senhaAntiga = senhaAntiga;
	}

	public String getSenhaNova() {
		return senhaNova;
	}

	public void setSenhaNova(String senhaNova) {
		this.senhaNova = senhaNova;
	}

	public String getSenhaConfirmacao() {
		return senhaConfirmacao;
	}

	public void setSenhaConfirmacao(String senhaConfirmacao) {
		this.senhaConfirmacao = senhaConfirmacao;
	}

	// IMPORT BD OLD FOR NEW
	private static br.com.project.dao.recoverBdOld.LoginDAO loginDAO = new br.com.project.dao.recoverBdOld.LoginDAO();
	private static br.com.project.dao.recoverBdOld.FasesDAO fasesDAO = new br.com.project.dao.recoverBdOld.FasesDAO();
	private static br.com.project.dao.recoverBdOld.MotivosDAO motivosDAO = new br.com.project.dao.recoverBdOld.MotivosDAO();
	private static br.com.project.dao.recoverBdOld.PessoaDAO pessoaDAO = new br.com.project.dao.recoverBdOld.PessoaDAO();
	private static br.com.project.dao.recoverBdOld.EmpresaDAO empresaDAO = new br.com.project.dao.recoverBdOld.EmpresaDAO();
	private static br.com.project.dao.recoverBdOld.AdvogadoDAO advogadoDAO = new br.com.project.dao.recoverBdOld.AdvogadoDAO();
	private static br.com.project.dao.recoverBdOld.ProcessoDAO processoDAO = new br.com.project.dao.recoverBdOld.ProcessoDAO();
	private static br.com.project.dao.recoverBdOld.ProcessoAssociadosDAO processoAssociadosDAO = new br.com.project.dao.recoverBdOld.ProcessoAssociadosDAO();
	private static br.com.project.dao.recoverBdOld.EnderecoCompletoDAO enderecoCompletoDAO = new br.com.project.dao.recoverBdOld.EnderecoCompletoDAO();
	
	public void importBD(){
		for (Integer x = 0; x < 10; x++) {
			if (x == 0)
				loginDAO.carregar();
			if (x == 1) {
				Cidade cidade = null;
				Login login = new br.com.project.dao.LoginDAO().obterPorId(new Long(500));
				CidadeDAO cidadeServico = new CidadeDAO();
				for (Cidade cidadeExt : PopulaCidade.listaCidade()) {
					cidade = cidadeServico.verificarCidade(cidadeExt.getEstado(), cidadeExt.getNome());
					if (cidade == null) {
						cidadeExt.setDataCadastro(new Date());
						cidadeExt.setLogin(login);
						cidadeExt.setDeletado(false);
						cidadeServico.salvar(cidadeExt);
					}
				}
			}
			if (x == 2)
				fasesDAO.carregar();
			if (x == 3)
				motivosDAO.carregar();
			if (x == 4)
				pessoaDAO.carregar();
			if (x == 5)
				empresaDAO.carregar();
			if (x == 6)
				advogadoDAO.carregar();
			if (x == 7)
				processoDAO.carregar();
			if (x == 8)
				processoAssociadosDAO.carregar();
			if (x == 9)
				enderecoCompletoDAO.carregar();
		}
	}
}
