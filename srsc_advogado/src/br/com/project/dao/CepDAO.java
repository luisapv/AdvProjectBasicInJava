package br.com.project.dao;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.project.modelo.Bairro;
import br.com.project.modelo.Cep;
import br.com.project.modelo.Cidade;
import br.com.project.modelo.EnumEstado;
import br.com.project.modelo.Logradouro;

public class CepDAO extends GenericDAO<Cep> {

	public CepDAO() {
		super(Cep.class);
	}

	@SuppressWarnings("finally")
	public Cep obterPorCep(String cep) {
		Cep cep1 = null;
		try {
			getOpenEntityManager();
			if (cep.isEmpty())
				return null;
			cep1 = entityManager.createQuery("from Cep where cep = :cep", Cep.class).setParameter("cep", cep)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
			return cep1;
		}
	}

	@SuppressWarnings("finally")
	public List<Cep> obterListPorCep(String cep) {
		List<Cep> cep1 = null;
		try {
			getOpenEntityManager();
			if (cep.isEmpty())
				return null;
			cep1 = entityManager.createQuery("from Cep where cep = :cep", Cep.class).setParameter("cep", cep)
					.getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
			return cep1;
		}
	}

	@SuppressWarnings("finally")
	public Cep obterPorCepOne(String cep) {
		Cep cep1 = null;
		try {
			getOpenEntityManager();
			if (cep.isEmpty())
				return null;
			cep1 = entityManager.createQuery("from Cep where cep = :cep limit 1", Cep.class).setParameter("cep", cep)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
			return cep1;
		}
	}

	@SuppressWarnings("finally")
	public Cep verificarCep(Cep cep) {
		Cep cepR = null;
		try {
			getOpenEntityManager();
			cepR = entityManager
					.createQuery(
							"from Cep where cep = :cep and estado = :estado and cidade = :cidade and bairro = :bairro and logradouro = :logradouro",
							Cep.class)
					.setParameter("cep", cep.getCep()).setParameter("estado", cep.getEstado())
					.setParameter("cidade", cep.getCidade().getId()).setParameter("bairro", cep.getBairro().getId())
					.setParameter("logradouro", cep.getLogradouro().getId()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
			return cepR;
		}
	}

	@SuppressWarnings("finally")
	public Cep verificarCep(String cepStr, EnumEstado estado, Cidade cidade, Bairro bairro, Logradouro logradouro) {
		Cep cepR = null;
		try {
			getOpenEntityManager();
			cepR = entityManager
					.createQuery(
							"from Cep where cep = :cepStr and estado = :estado and cidade = :cidade and bairro = :bairro and logradouro = :logradouro",
							Cep.class)
					.setParameter("cepStr", cepStr).setParameter("estado", estado).setParameter("cidade", cidade)
					.setParameter("bairro", bairro).setParameter("logradouro", logradouro).getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
			return cepR;
		}
	}
}
