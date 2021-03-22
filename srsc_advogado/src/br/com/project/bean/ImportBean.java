package br.com.project.bean;

import java.io.Serializable;
import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.project.dao.CidadeDAO;
import br.com.project.dao.popula.PopulaCidade;
import br.com.project.dao.recoverBdOld.AdvogadoDAO;
import br.com.project.dao.recoverBdOld.EmpresaDAO;
import br.com.project.dao.recoverBdOld.EnderecoCompletoDAO;
import br.com.project.dao.recoverBdOld.FasesDAO;
import br.com.project.dao.recoverBdOld.LoginDAO;
import br.com.project.dao.recoverBdOld.MotivosDAO;
import br.com.project.dao.recoverBdOld.PessoaDAO;
import br.com.project.dao.recoverBdOld.ProcessoAssociadosDAO;
import br.com.project.dao.recoverBdOld.ProcessoDAO;
import br.com.project.modelo.Cidade;
import br.com.project.modelo.Login;

@Controller(value = "importBean")
@Scope(value = "session")
public class ImportBean implements Serializable {

	private static final long serialVersionUID = -8352730541612689780L;

	private static LoginDAO loginDAO = new LoginDAO();
	private static FasesDAO fasesDAO = new FasesDAO();
	private static MotivosDAO motivosDAO = new MotivosDAO();
	private static PessoaDAO pessoaDAO = new PessoaDAO();
	private static EmpresaDAO empresaDAO = new EmpresaDAO();
	private static AdvogadoDAO advogadoDAO = new AdvogadoDAO();
	private static ProcessoDAO processoDAO = new ProcessoDAO();
	private static ProcessoAssociadosDAO processoAssociadosDAO = new ProcessoAssociadosDAO();
	private static EnderecoCompletoDAO enderecoCompletoDAO = new EnderecoCompletoDAO();
	
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
