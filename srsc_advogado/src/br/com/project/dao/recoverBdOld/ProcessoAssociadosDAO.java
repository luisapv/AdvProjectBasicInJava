package br.com.project.dao.recoverBdOld;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.project.dao.AdvogadoDAO;
import br.com.project.dao.FaseDAO;
import br.com.project.dao.MateriaDAO;
import br.com.project.dao.PessoaDAO;
import br.com.project.dao.ProcessoDAO;
import br.com.project.modelo.Advogado;
import br.com.project.modelo.Fase;
import br.com.project.modelo.Materia;
import br.com.project.modelo.Pessoa;
import br.com.project.modelo.Processo;

public class ProcessoAssociadosDAO extends GenericDAO {

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
	// @Autowired
	private FaseDAO faseServico = new FaseDAO();
	// @Autowired
	private MateriaDAO materiaServico = new MateriaDAO();

	public void carregar() {
		List<Processo> processsos = processoServico.listarTodos();

		List<Pessoa> autores;

		List<Advogado> advAutores;

		List<Pessoa> reus;

		List<Advogado> advReus;

		List<Fase> fases;

		List<Materia> materias;

		Pessoa pessoa;
		Advogado advogado;
		Fase fase;
		Materia materia;

		ResultSet result;
		try {
			for (Processo processo : processsos) {
				autores = new ArrayList<Pessoa>();
				advAutores = new ArrayList<Advogado>();
				reus = new ArrayList<Pessoa>();
				advReus = new ArrayList<Advogado>();
				fases = new ArrayList<Fase>();
				materias = new ArrayList<Materia>();

				pessoa = null;

				// AUTORES
				result = null;
				result = getSql(
						"SELECT * FROM base_mala_direta.autor where n_processo = \"" + processo.getProcesso() + "\"");
				while (result.next()) {
					if (result.getInt("cod_pessoa") != 0) {
						pessoa = pessoaServico.obterPorId(new Long(result.getInt("cod_pessoa") * 3 - 2));
						if (pessoa != null)
							autores.add(pessoa);
						pessoa = null;
					}

					if (result.getInt("cod_empresa") != 0) {
						pessoa = pessoaServico.obterPorId(new Long(result.getInt("cod_empresa") * 3 - 1));
						if (pessoa != null)
							autores.add(pessoa);
						pessoa = null;
					}
					if (result.getInt("cod_advogado") != 0) {
						advogado = advogadoServico.obterPorId(new Long(result.getInt("cod_advogado") * 3));
						if (advogado != null)
							advAutores.add(advogado);
						advogado = null;
					}
				}

				// REUS
				result = null;
				result = getSql(
						"SELECT * FROM base_mala_direta.reus where n_processo = \"" + processo.getProcesso() + "\"");
				while (result.next()) {
					if (result.getInt("cod_pessoa") != 0) {
						pessoa = pessoaServico.obterPorId(new Long(result.getInt("cod_pessoa") * 3 - 2));
						if (pessoa != null)
							reus.add(pessoa);
						pessoa = null;
					}

					if (result.getInt("cod_empresa") != 0) {
						pessoa = pessoaServico.obterPorId(new Long(result.getInt("cod_empresa") * 3 - 1));
						if (pessoa != null)
							reus.add(pessoa);
						pessoa = null;
					}

					if (result.getInt("cod_advogado") != 0) {
						advogado = advogadoServico.obterPorId(new Long(result.getInt("cod_advogado") * 3));
						if (advogado != null)
							advReus.add(advogado);
						advogado = null;
					}
				}

				// FASES
				result = null;
				result = getSql("SELECT * FROM base_mala_direta.processo_fases where n_processo = \""
						+ processo.getProcesso() + "\"");
				while (result.next()) {
					if (result.getInt("idFases") != 0) {
						fase = faseServico.obterPorId(new Long(result.getInt("idFases")));
						if (fase != null)
							fases.add(fase);
						fase = null;
					}
				}

				// MATERIAS
				result = null;
				result = getSql("SELECT * FROM base_mala_direta.processo_motivos where n_processo = \""
						+ processo.getProcesso() + "\"");
				while (result.next()) {
					if (result.getInt("idMotivos") != 0) {
						materia = materiaServico.obterPorId(new Long(result.getInt("idMotivos")));
						if (materia != null)
							materias.add(materia);
						materia = null;
					}
				}

				if (autores.size() > 0)
					processo.setAutores(autores);
				if (reus.size() > 0)
					processo.setReus(reus);
				if (advAutores.size() > 0)
					processo.setAdvAutor(advAutores);
				if (advReus.size() > 0)
					processo.setAdvReu(advReus);
				if (fases.size() > 0)
					processo.setFases(fases);
				if (materias.size() > 0)
					processo.setMaterias(materias);

				processoServico.salvar(processo);

			}
		} catch (SQLException e) {
			System.out.println("ProcessoAssociadosDAO");
			e.printStackTrace();
		}
	}
}
