package br.com.project.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	public static EntityManagerFactory getEntityManager() {
		return Persistence.createEntityManagerFactory("srsc_advogado");
	}
}
