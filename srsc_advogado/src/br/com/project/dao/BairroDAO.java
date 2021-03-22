package br.com.project.dao;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.project.modelo.Bairro;
import br.com.project.modelo.Cidade;

public class BairroDAO extends GenericDAO<Bairro> {

	public BairroDAO() {
		super(Bairro.class);
	}

	@SuppressWarnings({ "unchecked", "finally" })
	public List<Bairro> obterBairrosDaCidade(Cidade cidade) {
		List<Bairro> resultados = null;
		try {
			getOpenEntityManager();
			resultados = entityManager.createQuery("from Bairro where cidade = :cidade order by nome")
					.setParameter("cidade", cidade).getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
			return resultados;
		}
	}

	@SuppressWarnings("finally")
	public Bairro verificarBairro(Bairro bairro) {
		try {
			getOpenEntityManager();
			bairro = entityManager
					.createQuery("from Bairro where cidade = :cidade and lower(nome)=lower(:bairro)", Bairro.class)
					.setParameter("cidade", bairro.getCidade()).setParameter("bairro", bairro.getNome())
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
			return bairro;
		}
	}

	@SuppressWarnings("finally")
	public Bairro verificarBairro(Cidade cidade, Bairro bairro) {
		try {
			getOpenEntityManager();
			bairro = entityManager
					.createQuery("from Bairro where cidade = :cidade and lower(nome)=lower(:bairro)", Bairro.class)
					.setParameter("cidade", cidade).setParameter("bairro", bairro.getNome()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
			return bairro;
		}
	}

	@SuppressWarnings("finally")
	public Bairro verificarBairro(Cidade cidade, String bairroNome) {
		Bairro bairro = null;
		try {
			getOpenEntityManager();
			bairro = entityManager
					.createQuery("from Bairro where cidade = :cidade and lower(nome)=lower(:bairro)", Bairro.class)
					.setParameter("cidade", cidade).setParameter("bairro", bairroNome).getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
			return bairro;
		}
	}

	@SuppressWarnings("finally")
	public Bairro verificarBairro(String bairroNome) {
		Bairro bairro = null;
		try {
			getOpenEntityManager();
			bairro = entityManager.createQuery("from Bairro where lower(nome)=lower(:bairro)", Bairro.class)
					.setParameter("bairro", bairroNome).getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
			return bairro;
		}
	}

	@SuppressWarnings("finally")
	public List<Bairro> verificarBairros(String bairroNome) {
		List<Bairro> bairros = null;
		try {
			getOpenEntityManager();
			bairros = entityManager.createQuery("from Bairro where lower(nome)=lower(:bairro)", Bairro.class)
					.setParameter("bairro", bairroNome).getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
			return bairros;
		}
	}
}
