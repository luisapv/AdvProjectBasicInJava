package br.com.project.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

public abstract class GenericDAO<T> {

	private final Class<T> classe;

	// @PersistenceUnit(unitName = "srsc_advogado")

	private static EntityManagerFactory entityManagerFactory = JPAUtil.getEntityManager();
	
	protected EntityManager entityManager;

	protected void getOpenEntityManager(){
		entityManager = entityManagerFactory.createEntityManager();
	}
	
	public GenericDAO(Class<T> classe) {
		this.classe = classe;
	}

	public void salvar(T t) {
		try {
			getOpenEntityManager();
			entityManager.getTransaction().begin();
			entityManager.merge(t);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
		}
	}

	public T salvarReturn(T t) {
		try {
			getOpenEntityManager();
			entityManager.getTransaction().begin();
			t = (T) entityManager.merge(t);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
		}
		return t;
	}

	public void deletar(T t) {
		try {
			getOpenEntityManager();
			entityManager.getTransaction().begin();
			t = entityManager.merge(t);
			entityManager.remove(t);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
		}
	}

	@SuppressWarnings("finally")
	public List<T> listarTodos() {
		List<T> resultados = null;
		try {
			getOpenEntityManager();
			resultados = entityManager.createQuery("from " + classe.getName(), classe).getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			entityManager.close();
			return resultados;
		}
	}

	@SuppressWarnings("finally")
	public T obterPorId(Long id) {
		T t = null;
		try {
			getOpenEntityManager();
			t = entityManager.createQuery("from " + classe.getName() + " where id = :id", classe).setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException e) {
			System.err.println("ERRO: RETORNOU EM BRANCO!");
//			return null;
		} catch (Exception e) {
			System.err.println("ERRO: NÃO SEI O QUE ACONTECEU!");
//			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
			return t;
		}
	}
}
