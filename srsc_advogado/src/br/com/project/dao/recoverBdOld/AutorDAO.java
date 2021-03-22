package br.com.project.dao.recoverBdOld;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.project.dao.AdvogadoDAO;
import br.com.project.dao.PessoaDAO;
import br.com.project.dao.ProcessoDAO;
import br.com.project.modelo.Advogado;
import br.com.project.modelo.Pessoa;
import br.com.project.modelo.Processo;

public class AutorDAO extends GenericDAO {

	private static final long serialVersionUID = 1L;

	public ResultSet getResult(String where) throws SQLException {
		ResultSet result = getFindAll("autor", where);
		return result;
	}

	// @Autowired
	private ProcessoDAO processoServico = new ProcessoDAO();
	// @Autowired
	private PessoaDAO pessoaServico = new PessoaDAO();
	// @Autowired
	private AdvogadoDAO advogadoServico = new AdvogadoDAO();

	@SuppressWarnings("unused")
	public void carregar() {
		ResultSet result;

		List<Processo> processos = processoServico.listarTodos();

		List<Pessoa> pessoas;

		List<Advogado> advogados;

		try {
			for (Processo processo : processos) {
				result = getResult(" where n_processo='" + processo.getProcesso() + "'");
				if (result != null) {
					pessoas = new ArrayList<>();
					advogados = new ArrayList<>();
					while (result.next()) {
						Long idAdvogado = new Long(result.getInt("cod_advogado") * 3);
						Long idPessoa = new Long(result.getInt("cod_pessoa") * 3 - 2);
						Long idEmpresa = new Long(result.getInt("cod_empresa") * 3 - 1);
						Long pessoa = null;

						if (idPessoa != null) {
							pessoa = idPessoa;
						} else if (idEmpresa != null) {
							pessoa = idEmpresa;
						}

						if (pessoa != null)
							pessoas.add(pessoaServico.obterPorId(pessoa));
						if (idAdvogado != null)
							advogados.add(advogadoServico.obterPorId(idAdvogado));
					}
					if (pessoas.size() > 0 && pessoas != null)
						processo.setAutores(pessoas);
					if (advogados.size() > 0 && advogados != null)
						processo.setAdvAutor(advogados);
					
					if ((pessoas.size() > 0 && pessoas != null) || (advogados.size() > 0 && advogados != null))
						processoServico.salvar(processo);
				}
			}
		} catch (SQLException e) {
			System.out.println("AUTOR");
			e.printStackTrace();
		}
	}
}
