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
import br.com.project.modelo.Materia;
import br.com.project.modelo.PerfilPreConfiguracao;
import br.com.project.servico.MateriaServico;
import br.com.project.util.Mensagem;

@Controller(value = "materiaBean")
@Scope(value = "session")
public class MateriaBean implements Serializable {

	private static final long serialVersionUID = 2697264978280707957L;

	private Materia materia;
	private List<Materia> materias = new ArrayList<Materia>();

	@Autowired
	private MateriaServico materiaServico;

	@Autowired
	private LoginController loginController;

	private Login loginLogado;

	private List<EnumEstado> estados = Arrays.asList(EnumEstado.values());
	private List<Materia> materiasFiltrados;
	private List<Boolean> listToggle = Arrays.asList(true, true, true, true, true);

	public void iniciarBean() {
		atualizarListToogleGrid();
		atualizarListaMaterias();
		this.setLoginLogado(loginController.obterLoginLogado());
	}

	public void atualizarListToogleGrid() {
		// listToggle = loginLogado.columnVisibleDataTable(EnumPaginas.ADVOGADO.toString(), 5);
	}

	public void atualizarListaMaterias() {
		this.materias = materiaServico.listarTodos();
	}

	public void novoMateria() {
		this.materia = new Materia();
		this.resetFormCidade();
		Mensagem.mensagemErro(null, null, "Novo Materia!");
	}

	public void novoMateriaCancelar() {
		if (this.materia.getId() != null) {
			Mensagem.mensagemErro(null, null, "A edição do Materia foi cancelada com sucesso!");
		} else
			Mensagem.mensagemErro(null, null, "A inclusão do Materia foi cancelada com sucesso!");
		this.materia = null;
	}

	public void materiaNulo() {
		this.materia = null;
	}

	public void salvar() {
		this.materia.setDataCadastro(new Date());
		this.materia.setLogin(loginLogado);
		this.materiaServico.salvar(this.materia);
		atualizarListaMaterias();
		this.materia = null;
		Mensagem.mensagemErro(null, null, "Materia salvo com sucesso!");
	}

	public void editar() {
		if (this.materia != null) {
			this.materia = materiaServico.obterPorId(this.materia.getId());
			this.resetFormCidade();
			Mensagem.mensagemErro(null, null, "Materia em modo de Edição!");
		}
	}

	public void deletar() {
		this.materia = materiaServico.obterPorId(this.materia.getId());
		this.materiaServico.excluir(this.materia);
		if (this.materiasFiltrados != null)
			this.materiasFiltrados.remove(this.materia);
		this.materia = null;
		atualizarListaMaterias();
		Mensagem.mensagemErro(null, null, "Materia foi desativado com sucesso!");
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
		this.materia = (Materia) event.getObject();
		this.editar();
	}

	public void resetFormCidade() {
		PrimeFaces.current().resetInputs("DialogMateriaForm");
	}

	public void onToggle(ToggleEvent e) {
		listToggle.clear();
		listToggle.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
	}

	public PerfilPreConfiguracao getPerfilPreConfiguracaoPage() {
		return loginController.obterPerfilPreConfiguracaoLoginPage(EnumPaginas.ADVOGADO);
	}

	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}

	public List<Materia> getMaterias() {
		return materias;
	}

	public void setMaterias(List<Materia> materias) {
		this.materias = materias;
	}

	public MateriaServico getMateriaServico() {
		return materiaServico;
	}

	public void setMateriaServico(MateriaServico materiaServico) {
		this.materiaServico = materiaServico;
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

	public List<Materia> getMateriasFiltrados() {
		return materiasFiltrados;
	}

	public void setMateriasFiltrados(List<Materia> materiasFiltrados) {
		this.materiasFiltrados = materiasFiltrados;
	}

	public List<Boolean> getListToggle() {
		return listToggle;
	}

	public void setListToggle(List<Boolean> listToggle) {
		this.listToggle = listToggle;
	}
}
