package br.com.project.dao.recoverBdOld;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.project.dao.MateriaDAO;
import br.com.project.dao.ProcessoDAO;
import br.com.project.modelo.Materia;
import br.com.project.modelo.Processo;

public class Processo_motivosDAO extends GenericDAO {

	private static final long serialVersionUID = 1L;

	public ResultSet getResult(String where) throws SQLException {
		ResultSet result = getFindAll("processo_motivos", where);
		return result;
	}

	// @Autowired
	private ProcessoDAO processoServico = new ProcessoDAO();
	// @Autowired
	private MateriaDAO MateriaServico = new MateriaDAO();

	public void carregar() {
		ResultSet result;

		List<Processo> processos = processoServico.listarTodos();

		List<Materia> Materias;

		try {
			for (Processo processo : processos) {
				result = getResult(" where n_processo='" + processo.getProcesso() + "'");
				Materias = new ArrayList<>();

				while (result.next()) {
					Long idMateria = new Long(result.getInt("idMotivos"));

					if (idMateria != null)
						Materias.add(MateriaServico.obterPorId(idMateria));
				}
				if (Materias.size() > 0 && Materias != null) {
					processo.setMaterias(Materias);
					processoServico.salvar(processo);
				}
			}
		} catch (SQLException e) {
			System.out.println("PROCESSO MOTIVO");
			e.printStackTrace();
		}
	}
}
