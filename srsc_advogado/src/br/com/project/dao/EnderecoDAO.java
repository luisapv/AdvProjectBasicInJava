package br.com.project.dao;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.project.modelo.Cep;
import br.com.project.modelo.Endereco;
import br.com.project.modelo.Pessoa;

public class EnderecoDAO extends GenericDAO<Endereco> {

	public EnderecoDAO() {
		super(Endereco.class);
	}

	@SuppressWarnings("finally")
	public List<Endereco> obterUmEnderecoPorPessoa(Pessoa pessoa) {
		List<Endereco> resultados = null;
		try {
			getOpenEntityManager();
			resultados = entityManager.createQuery("from Endereco where pessoa = :pessoa", Endereco.class)
					.setParameter("pessoa", pessoa.getId()).getResultList();
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
	public Endereco verificaPessoa(Pessoa pessoa) {
		Endereco end = null;
		try {
			getOpenEntityManager();
			end = entityManager
					.createQuery(
							"from Endereco where pessoa = :pessoa",
							Endereco.class)
					.setParameter("pessoa", pessoa.getId()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
			return end;
		}
	}

	@SuppressWarnings("finally")
	public Endereco verificaEnderecoLogin(Cep idCep) {
		Endereco end = null;
		try {
			getOpenEntityManager();
			end = entityManager
					.createQuery(
							"from Endereco where cep = :cep AND pessoa = null",
							Endereco.class)
					.setParameter("cep", idCep.getId()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// Executa esse bloco independente do que acontece acima! :D
			entityManager.close();
			return end;
		}
	}
}
