package br.com.project.dao.recoverBdOld;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import br.com.project.modelo.Advogado;
import br.com.project.modelo.Fase;
import br.com.project.modelo.Login;
import br.com.project.modelo.Materia;
import br.com.project.modelo.Pessoa;
import br.com.project.modelo.Processo;

public class ProcessoDAO extends GenericDAO {

	private static final long serialVersionUID = 1L;

	// @Autowired
	br.com.project.dao.ProcessoDAO processoServico = new br.com.project.dao.ProcessoDAO();
	// @Autowired
	br.com.project.dao.LoginDAO loginServico = new br.com.project.dao.LoginDAO();
	// @Autowired
	br.com.project.dao.PessoaDAO pessoaServico = new br.com.project.dao.PessoaDAO();
	// @Autowired
	br.com.project.dao.AdvogadoDAO advogadoServico = new br.com.project.dao.AdvogadoDAO();
	// @Autowired
	br.com.project.dao.FaseDAO faseServico = new br.com.project.dao.FaseDAO();
	// @Autowired
	br.com.project.dao.MateriaDAO materiaServico = new br.com.project.dao.MateriaDAO();

	public ResultSet getResult() throws SQLException {
		ResultSet result = getFindAll("processo", "");
		return result;
	}

	@SuppressWarnings({ "unused"})
	public void carregar() {
		ResultSet result;
		try {
			result = getResult();
			Processo processo = null;
			Login login;
			List<Advogado> advAutores;
			List<Advogado> advReus;
			List<Pessoa> autores;
			List<Pessoa> reus;
			List<Fase> fases;
			List<Materia> materias;

			while (result.next()) {
				advAutores = null;
				advReus = null;
				autores = null;
				reus = null;
				fases = null;
				materias = null;

				String nProcesso = result.getString("n_processo");
				String descricao = result.getString("descricao");
				String arquivo = "C://srsc_advogado//arquivos//processos//" + nProcesso + "//";
				String juizDaSentenca = result.getString("juizDaSentenca");
				Integer trtTurma = result.getInt("trtTurma");
				String trtRelator = result.getString("trtRelator");
				Integer tstTurma = result.getInt("tstTurma");
				String tstRelator = result.getString("tstRelator");
				Date dataArquivo = result.getDate("dataArquivo");
				Date dataCadastro = result.getDate("dataCadastro");
				Long idLogin = new Long(result.getInt("idlogin"));

				if (idLogin == null) {
					idLogin = new Long(500);
				}
				login = loginServico.obterPorId(idLogin);

				if (dataCadastro == null || dataCadastro.toString().equals("0000-00-00")) {
					dataCadastro = getDate(new java.util.Date());
				}

				if(dataArquivo!=null)
				if (dataArquivo.toString().equals("0000-00-00")) {
					dataArquivo = null;
				}

//				Materia materia = null;
//				materias = new ArrayList<Materia>();
//
//				ResultSet set = new MotivosDAO().getFindAll("processo_motivos", "");
//				while (set.next()) {
//					if (set.getString("n_processo").equals(nProcesso)) {
//						materia = materiaServico.obterPorId(new Long(set.getInt("idMotivos")));
//						if (materia != null)
//							materias.add(materia);
//						materia = null;
//					}
//				}
//
//				Fase fase = null;
//				fases = new ArrayList<Fase>();
//
//				set = new FasesDAO().getFindAll("processo_fases", "");
//				while (set.next()) {
//					if (set.getString("n_processo").equals(nProcesso)) {
//						fase = faseServico.obterPorId(new Long(set.getInt("idFases")));
//						if (fase != null) {
//							System.err.println("ID: " + fase.getId().toString() + " - NOME: " + fase.getNome());
//							fases.add(fase);
//						}
//						fase = null;
//					}
//				}
//
//				Advogado advogado = null;
//				Pessoa pessoa = null;
//				advAutores = new ArrayList<Advogado>();
//				autores = new ArrayList<Pessoa>();
//
//				set = new AutorDAO().getFindAll("autor", "");
//				while (set.next()) {
//					if (set.getString("n_processo").equals(nProcesso)) {
//						if (set.getString("cod_pessoa") != null) {
//							pessoa = pessoaServico.obterPorId(new Long(set.getInt("cod_pessoa") * 3 - 2));
//							if (pessoa != null)
//								autores.add(pessoa);
//						} else if (set.getString("cod_empresa") != null) {
//							pessoa = pessoaServico.obterPorId(new Long(set.getInt("cod_empresa") * 3 - 1));
//							if (pessoa != null)
//								autores.add(pessoa);
//						}
//
//						advogado = advogadoServico.obterPorId(new Long(set.getInt("cod_advogado") * 3));
//						if (advogado != null)
//							advAutores.add(advogado);
//
//						pessoa = null;
//						advogado = null;
//					}
//				}
//
//				advReus = new ArrayList<Advogado>();
//				reus = new ArrayList<Pessoa>();
//
//				set = new AutorDAO().getFindAll("reus", "");
//				while (set.next()) {
//					if (set.getString("n_processo").equals(nProcesso)) {
//						if (set.getString("cod_pessoa") != null)
//							pessoa = pessoaServico.obterPorId(new Long(set.getInt("cod_pessoa") * 3 - 2));
//						if (pessoa != null)
//							reus.add(pessoa);
//						else if (set.getString("cod_empresa") != null)
//							pessoa = pessoaServico.obterPorId(new Long(set.getInt("cod_empresa") * 3 - 1));
//						if (pessoa != null)
//							reus.add(pessoa);
//
//						advogado = advogadoServico.obterPorId(new Long(set.getInt("cod_advogado") * 3));
//						if (advogado != null)
//							advReus.add(advogado);
//
//						pessoa = null;
//						advogado = null;
//					}
//				}
				
				processo = new Processo();
				processo.setProcesso(nProcesso);
				processo.setDescricao(descricao);
				processo.setArquivo(arquivo);
				processo.setDataArquivo(dataArquivo);
				processo.setJuizSenteca(juizDaSentenca);
				processo.setTrtRelator(trtRelator);
				processo.setTrtTurma(trtTurma);
				processo.setTstRelator(tstRelator);
				processo.setTstTurma(tstTurma);
				processo.setDataCadastro(dataCadastro);
				processo.setLogin(login);

//				if (autores.size()>0 && autores != null)
//					processo.setAutores(autores);
//				if (reus.size()>0 && reus != null)
//					processo.setReus(reus);
//				if (advAutores.size()>0 && advAutores != null)
//					processo.setAdvAutor(advAutores);
//				if (advReus.size()>0 && advReus != null)
//					processo.setAdvReu(advReus);
//				if (fases.size()>0 && fases != null)
//					processo.setFases(fases);
//				if (materias.size()>0 && materias != null)
//					processo.setMaterias(materias);

				processoServico.salvar(processo);
			}
		} catch (SQLException e) {
			System.out.println("PROCESSO");
			e.printStackTrace();
		}
	}
}
