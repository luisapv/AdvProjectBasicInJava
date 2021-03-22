package br.com.project.dao;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.project.modelo.Cidade;
import br.com.project.modelo.EnumEstado;

public class CidadeDAO extends GenericDAO<Cidade> {

	public CidadeDAO() {
		super(Cidade.class);
	}

	@SuppressWarnings({ "unchecked", "finally" })
	public List<Cidade> obterCidadesDoEstado(EnumEstado estado) {
		List<Cidade> cidades = null;
		try {
			getOpenEntityManager();
			cidades = entityManager.createQuery("from Cidade where estado = :uf").setParameter("uf", estado)
					.getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
			return cidades;
		}
	}

	@SuppressWarnings("finally")
	public Cidade verificarCidade(Cidade cidade) {
		Cidade cidadeNew = null;
		try {
			getOpenEntityManager();
			cidadeNew = entityManager
					.createQuery("from Cidade where estado = :uf and lower(nome)=lower(:cidade)", Cidade.class)
					.setParameter("uf", cidade.getEstado()).setParameter("cidade", cidade.getNome()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
			return cidadeNew;
		}
	}

	@SuppressWarnings("finally")
	public Cidade verificarCidade(EnumEstado estado, Cidade cidade) {
		try {
			getOpenEntityManager();
			cidade = entityManager
					.createQuery("from Cidade where estado = :uf and lower(nome)=lower(:cidade)", Cidade.class)
					.setParameter("uf", estado).setParameter("cidade", cidade.getNome()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
			return cidade;
		}
	}

	@SuppressWarnings("finally")
	public Cidade verificarCidade(EnumEstado estado, String cidadeNome) {
		Cidade cidade = null;
		try {
			getOpenEntityManager();
			cidade = entityManager
					.createQuery("from Cidade where estado = :uf and lower(nome)=lower(:cidade)", Cidade.class)
					.setParameter("uf", estado).setParameter("cidade", cidadeNome).getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
			return cidade;
		}
	}

	@SuppressWarnings("finally")
	public Cidade verificarCidade(String cidadeNome) {
		Cidade cidade = null;
		try {
			getOpenEntityManager();
			cidade = entityManager.createQuery("from Cidade where lower(nome)=lower(:cidade)", Cidade.class)
					.setParameter("cidade", cidadeNome).getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
			return cidade;
		}
	}

	@SuppressWarnings("finally")
	public List<Cidade> verificarCidades(String cidadeNome) {
		List<Cidade> cidades = null;
		try {
			getOpenEntityManager();
			cidades = entityManager.createQuery("from Cidade where lower(nome)=lower(:cidade)", Cidade.class)
					.setParameter("cidade", cidadeNome).getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
			return cidades;
		}
	}
}
