package br.com.project.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class Conection {

	private static EntityManagerFactory entityManagerFactory = JPAUtil.getEntityManager();
	
	private static EntityManager entityManager = entityManagerFactory.createEntityManager();

	public static EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public static EntityManager getEntityManager() {
		return entityManager;
	}
	
}
