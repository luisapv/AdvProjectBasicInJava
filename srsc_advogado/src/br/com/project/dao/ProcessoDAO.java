package br.com.project.dao;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.project.modelo.Advogado;
import br.com.project.modelo.Fase;
import br.com.project.modelo.Materia;
import br.com.project.modelo.PessoaFisica;
import br.com.project.modelo.PessoaJuridica;
import br.com.project.modelo.Processo;

public class ProcessoDAO extends GenericDAO<Processo> {

	public ProcessoDAO() {
		super(Processo.class);
	}

	@SuppressWarnings({ "unchecked", "finally" })
	public List<String> obterJuizes(String juiz) {
		List<String> resultados = null;
		try {
			getOpenEntityManager();
			resultados = (List<String>) entityManager
					.createQuery("select juizSenteca from Processo where lower(juizSenteca) like lower(:juiz)")
					.setParameter("juiz", juiz + "%").getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
			return resultados;
		}
	}

	@SuppressWarnings({ "unchecked", "finally" })
	public List<String> obterTrtRelator(String trtRelator) {
		List<String> resultados = null;
		try {
			getOpenEntityManager();
			resultados = (List<String>) entityManager
					.createQuery("select trtRelator from Processo where lower(trtRelator) like lower(:relator)")
					.setParameter("relator", trtRelator + "%").getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
			return resultados;
		}
	}

	@SuppressWarnings({ "unchecked", "finally" })
	public List<String> obterTstRelator(String tstRelator) {
		List<String> resultados = null;
		try {
			getOpenEntityManager();
			resultados = (List<String>) entityManager
					.createQuery("select tstRelator from Processo where lower(tstRelator) like lower(:relator)")
					.setParameter("relator", tstRelator + "%").getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
			return resultados;
		}
	}

	@SuppressWarnings({ "unchecked", "finally" })
	public List<Advogado> obterAdvogado(String advogado) {
		List<Advogado> resultados = null;
		try {
			getOpenEntityManager();
			resultados = (List<Advogado>) entityManager
					.createQuery("from Advogado where lower(nome) like lower(:advogado) or identidade like :advogado")
					.setParameter("advogado", advogado + "%").getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
			return resultados;
		}
	}

	@SuppressWarnings({ "unchecked", "finally" })
	public List<PessoaFisica> obterPessoaFisica(String pessoaFisica) {
		List<PessoaFisica> resultados = null;
		try {
			getOpenEntityManager();
			resultados = (List<PessoaFisica>) entityManager
					.createQuery(
							"from PessoaFisica where lower(nome) like lower(:pessoaFisica)' or documento like :pessoaFisica")
					.setParameter("pessoaFisica", pessoaFisica + "%").getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
			return resultados;
		}
	}

	@SuppressWarnings({ "unchecked", "finally" })
	public List<PessoaJuridica> obterPessoaJuridica(String pessoaJuridica) {
		List<PessoaJuridica> resultados = null;
		try {
			getOpenEntityManager();
			resultados = (List<PessoaJuridica>) entityManager
					.createQuery(
							"from PessoaJuridica where lower(nome) like lower(:pessoaJuridica) or documento like :pessoaJuridica")
					.setParameter("pessoaJuridica", pessoaJuridica + "%").getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
			return resultados;
		}
	}

	@SuppressWarnings({ "unchecked", "finally" })
	public List<Fase> obterFase(String fase) {
		List<Fase> resultados = null;
		try {
			getOpenEntityManager();
			resultados = (List<Fase>) entityManager
					.createQuery("from Fase where lower(nome) like lower(:fase) or sigla like :fase")
					.setParameter("fase", fase).getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
			return resultados;
		}
	}

	@SuppressWarnings({ "unchecked", "finally" })
	public List<Materia> obterMateria(String materia) {
		List<Materia> resultados = null;
		try {
			getOpenEntityManager();
			resultados = (List<Materia>) entityManager
					.createQuery("from Materia where lower(nome) like lower(:materia) or sigla like :materia")
					.setParameter("materia", materia + "%").getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
			return resultados;
		}
	}

	@SuppressWarnings("finally")
	public Processo obterProcesso(String processo) {
		Processo resultado = null;
		try {
			getOpenEntityManager();
			resultado = (Processo) entityManager.createQuery("from Processo where processo = :processo", Processo.class)
					.setParameter("processo", processo).getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
			return resultado;
		}
	}
}
