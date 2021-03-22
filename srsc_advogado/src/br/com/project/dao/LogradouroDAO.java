package br.com.project.dao;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.project.modelo.Bairro;
import br.com.project.modelo.EnumTipoLogradouro;
import br.com.project.modelo.Logradouro;

public class LogradouroDAO extends GenericDAO<Logradouro> {

	public LogradouroDAO() {
		super(Logradouro.class);
	}

	@SuppressWarnings("finally")
	public Logradouro verificarLogradouro(Logradouro logradouro) {
		try {
			getOpenEntityManager();
			logradouro = entityManager
					.createQuery("from Logradouro where bairro = :bairro and lower(nome)=lower(:logradouro)",
							Logradouro.class)
					.setParameter("bairro", logradouro.getBairro()).setParameter("logradouro", logradouro.getNome())
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
			return logradouro;
		}
	}

	@SuppressWarnings("finally")
	public Logradouro verificarLogradouro(Bairro bairro, Logradouro logradouro) {
		try {
			getOpenEntityManager();
			logradouro = entityManager
					.createQuery("from Logradouro where bairro = :bairro and lower(nome)=lower(:logradouro)",
							Logradouro.class)
					.setParameter("bairro", bairro).setParameter("logradouro", logradouro).getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
			return logradouro;
		}
	}

	@SuppressWarnings("finally")
	public Logradouro verificarLogradouro(Bairro bairro, String logradouroStr, EnumTipoLogradouro tipoLogradouro) {
		Logradouro logradouro = null;
		try {
			getOpenEntityManager();
			logradouro = entityManager
					.createQuery(
							"from Logradouro where bairro = :bairro and lower(nome)=lower(:logradouro) and tipoLogradouro = :tipoLogradouro",
							Logradouro.class)
					.setParameter("bairro", bairro).setParameter("logradouro", logradouroStr)
					.setParameter("tipoLogradouro", tipoLogradouro).getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
			return logradouro;
		}
	}

	@SuppressWarnings("finally")
	public Logradouro verificarLogradouro(Bairro bairro, String logradouroNome) {
		Logradouro logradouro = null;
		try {
			getOpenEntityManager();
			logradouro = entityManager
					.createQuery("from Logradouro where bairro = :bairro and lower(nome)=lower(:logradouro)",
							Logradouro.class)
					.setParameter("bairro", bairro).setParameter("logradouro", logradouroNome).getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
			return logradouro;
		}
	}

	@SuppressWarnings("finally")
	public Logradouro verificarLogradouro(String logradouroNome) {
		Logradouro logradouro = null;
		try {
			getOpenEntityManager();
			logradouro = entityManager
					.createQuery("from Logradouro where lower(nome)=lower(:logradouro)", Logradouro.class)
					.setParameter("logradouro", logradouroNome).getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
			return logradouro;
		}
	}

	@SuppressWarnings("finally")
	public List<Logradouro> verificarLogradouros(String logradouroNome) {
		List<Logradouro> logradouros = null;
		try {
			getOpenEntityManager();
			logradouros = entityManager
					.createQuery("from Logradouro where lower(nome)=lower(:logradouro)", Logradouro.class)
					.setParameter("logradouro", logradouroNome).getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
			return logradouros;
		}
	}
}
