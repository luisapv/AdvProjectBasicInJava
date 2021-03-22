package br.com.project.dao.recoverBdOld;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.project.dao.FaseDAO;
import br.com.project.dao.ProcessoDAO;
import br.com.project.modelo.Fase;
import br.com.project.modelo.Processo;

public class Processo_fasesDAO extends GenericDAO {

	private static final long serialVersionUID = 1L;

	public ResultSet getResult(String where) throws SQLException {
		ResultSet result = getFindAll("processo_fases", where);
		return result;
	}

	// @Autowired
	private ProcessoDAO processoServico = new ProcessoDAO();
	// @Autowired
	private FaseDAO faseServico = new FaseDAO();

	public void carregar() {
		ResultSet result;

		List<Processo> processos = processoServico.listarTodos();

		List<Fase> fases;

		try {
			for (Processo processo : processos) {
				result = getResult(" where n_processo='" + processo.getProcesso() + "'");
				fases = new ArrayList<>();
				while (result.next()) {
					Long idFase = new Long(result.getInt("idFases"));

					if (idFase != null)
						fases.add(faseServico.obterPorId(idFase));
				}
				if (fases.size() > 0 && fases != null) {
					processo.setFases(fases);
					processoServico.salvar(processo);
				}
			}
		} catch (SQLException e) {
			System.out.println("PROCESSO FASE");
			e.printStackTrace();
		}
	}
}
