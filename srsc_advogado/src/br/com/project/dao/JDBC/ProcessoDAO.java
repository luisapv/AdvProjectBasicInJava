package br.com.project.dao.JDBC;

import java.io.File;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.project.modelo.dataTable.Processo;

public class ProcessoDAO extends GenericDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<Processo> getLazyProcessoDataTale() throws SQLException {
		String sql = "SELECT p.processo, p.arquivo, p.dataArquivo, p.juizSenteca, p.trtTurma, p.trtRelator,"
				+ " p.tstTurma, p.tstRelator, l.nome AS cadastradoPor, p.dataCadastro,"
				+ " pAutor.nome AS autores,"
				+ " pAdvAutor.nome AS advAutor,"
				+ " pReu.nome AS reus,"
				+ " pAdvReu.nome AS advReu,"
				+ " REPLACE(GROUP_CONCAT(f.nome),',','; ') AS fases,"
				+ " REPLACE(GROUP_CONCAT(m.nome),',','; ') AS materias"
				+ " FROM processo AS p"
				+ " LEFT JOIN processo_autor AS pa ON p.processo = pa.processo"
				+ " LEFT JOIN pessoa AS pAutor ON pa.pessoa = pAutor.id"
				+ " LEFT JOIN processo_reu AS pr ON p.processo = pr.processo"
				+ " LEFT JOIN pessoa AS pReu ON pr.pessoa = pReu.id"
				+ " LEFT JOIN processo_advogado_autor AS paa ON p.processo = paa.processo"
				+ " LEFT JOIN pessoa AS pAdvAutor ON paa.advogado = pAdvAutor.id"
				+ " LEFT JOIN processo_advogado_reu as par ON par.processo = p.processo"
				+ " LEFT JOIN pessoa AS pAdvReu ON par.advogado = pAdvReu.id"
				+ " LEFT JOIN processo_fase AS pf ON pf.processo = p.processo" 
				+ " LEFT JOIN fase AS f ON pf.fase = f.id"
				+ " LEFT JOIN processo_materia AS pm ON pm.processo = p.processo"
				+ " LEFT JOIN materia AS m ON pm.materia = m.id"
				+ " LEFT JOIN login AS l ON p.login = l.id"
				+ " GROUP BY p.processo";
//		String sql = "";
		List<Processo> processos = new ArrayList<Processo>();
		Processo processo = null;
		File file = null;

		ResultSet result = getFindSql(sql);

		while (result.next()) {
			processo = new br.com.project.modelo.dataTable.Processo();

			processo.setProcesso(result.getString("processo"));
			processo.setArquivo(result.getString("arquivo"));
			try {
				file = new File(processo.getArquivo());
				processo.setArquivoEmpty(file.exists());
			} catch (Exception e) {
				processo.setArquivoEmpty(false);
			} 
			processo.setDataArquivo(result.getDate("dataArquivo"));
			processo.setJuizSenteca(result.getString("juizSenteca"));
			processo.setTrtTurma(result.getInt("trtTurma"));
			processo.setTrtRelator(result.getString("TrtRelator"));
			processo.setTstTurma(result.getInt("tstTurma"));
			processo.setTstRelator(result.getString("TstRelator"));
			processo.setAutores(result.getString("autores"));
			processo.setAdvAutor(result.getString("advAutor"));
			processo.setReus(result.getString("reus"));
			processo.setAdvReu(result.getString("advReu"));
			processo.setFases(result.getString("fases"));
			processo.setMaterias(result.getString("materias"));
			processo.setCadastradoPor(result.getString("cadastradoPor"));
			processo.setDataCadastro(result.getDate("dataCadastro"));

			processos.add(processo);
		}

		return processos;
	}

	public void saveProcessoAutor(String values) throws SQLException{
		super.save("processo_autor", "processo, pessoa", values);
	}

	public void saveProcessoAdvAutor(String values) throws SQLException{
		super.save("processo_advogado_reu", "processo, advogado", values);
	}

	public void saveProcessoReu(String values) throws SQLException{
		super.save("processo_reu", "processo, pessoa", values);
	}

	public void saveProcessoAdvReu(String values) throws SQLException{
		super.save("processo_advogado_reu", "processo, advogado", values);
	}

	public void saveProcessoFase(String values) throws SQLException{
		super.save("processo_fase", "processo, fase", values);
	}

	public void saveProcessoMateria(String values) throws SQLException{
		super.save("processo_materia", "processo, materia", values);
	}
}
