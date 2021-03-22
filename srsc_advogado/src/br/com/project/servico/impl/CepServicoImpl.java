package br.com.project.servico.impl;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.project.modelo.Bairro;
import br.com.project.modelo.Cep;
import br.com.project.modelo.Cidade;
import br.com.project.modelo.EnumEstado;
import br.com.project.modelo.Logradouro;
import br.com.project.servico.CepServico;

@Service(value = "cepServico")
@Transactional
public class CepServicoImpl implements CepServico {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void salvar(Cep cep) {
		entityManager.merge(cep);
	}

	@Override
	public Cep salvarReturn(Cep cep) {
		return entityManager.merge(cep);
	}

	@Override
	public void excluir(Cep cep) {
		cep = entityManager.merge(cep);
		entityManager.remove(cep);
	}

	@Override
	public List<Cep> listarTodos() {
		return entityManager.createNamedQuery("Cep.LitarTodos", Cep.class).getResultList();
	}

	@Override
	public Cep obterPorId(Long id) {
		return entityManager.createQuery("from Cep where id = :id", Cep.class).setParameter("id", id)
				.getSingleResult();
	}

	@Override
	public Cep obterPorCep(String cep) {
		if (cep.isEmpty())
			return null;
		return entityManager.createQuery("from Cep where cep = :cep", Cep.class).setParameter("cep", cep)
				.getSingleResult();
	}

	@Override
	public List<Cep> obterListPorCep(String cep) {
		if (cep.isEmpty())
			return null;
		return entityManager.createQuery("from Cep where cep = :cep", Cep.class).setParameter("cep", cep)
				.getResultList();
	}

	@Override
	public List<Cep> obterListCepInicia(String cep) {
		if (cep.isEmpty())
			return null;
		return entityManager.createQuery("from Cep where cep like :cep", Cep.class).setParameter("cep", cep + "%")
				.getResultList();
	}

	@Override
	public Cep verificarCep(String cep, EnumEstado estado, Cidade cidade, Bairro bairro, Logradouro logradouro) {
		return entityManager.createQuery("from Cep where cep = :cep and estado = :estado and cidade = :cidade ande bairro = :bairro and logradouro = :logradouro", Cep.class).setParameter("cep", cep).setParameter("estado", estado).setParameter("cidade", cidade).setParameter("bairro", bairro).setParameter("logradouro", logradouro).getSingleResult();
	}

	public List<br.com.project.modelo.dataTable.Cep> getLazyCepDataTable(){
		try {
			return new br.com.project.dao.JDBC.CepDAO().getLazyCepDataTale();
		} catch (SQLException e) {
			return null;
		}
	}

}
