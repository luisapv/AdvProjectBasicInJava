package br.com.project.servico.impl;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.project.modelo.PessoaJuridica;
import br.com.project.servico.PessoaJuridicaServico;

@Service(value = "pessoaJuridicaServico")
@Transactional
public class PessoaJuridicaServicoImpl implements PessoaJuridicaServico {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void salvar(PessoaJuridica pessoaJuridica) {
		entityManager.merge(pessoaJuridica);
	}

	@Override
	public PessoaJuridica salvarReturn(PessoaJuridica pessoaJuridica) {
		return (PessoaJuridica) entityManager.merge(pessoaJuridica);
	}

	@Override
	public void excluir(PessoaJuridica pessoaJuridica) {
		pessoaJuridica = entityManager.merge(pessoaJuridica);
		entityManager.remove(pessoaJuridica);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PessoaJuridica> listarTodos() {
		return entityManager.createNamedQuery("PessoaJuridica.LitarTodos").getResultList();
	}

	@Override
	public PessoaJuridica obterPorId(Long id) {
		return entityManager.createQuery("from PessoaJuridica where id = :id", PessoaJuridica.class)
				.setParameter("id", id).getSingleResult();
	}

	@SuppressWarnings({ "unchecked", "null" })
	@Override
	public List<PessoaJuridica> obterPessoaJuridica(String pessoaJuridica) {
		if (pessoaJuridica != null || !pessoaJuridica.isEmpty())
			if (pessoaJuridica.equalsIgnoreCase("*"))
				return listarTodos();
			else
				return (List<PessoaJuridica>) entityManager
						.createQuery(
								"from PessoaJuridica where lower(nome) like lower(:pessoaJuridica) or documento like :pessoaJuridica")
						.setParameter("pessoaJuridica", pessoaJuridica + "%").getResultList();
		else
			return null;
	}

	public List<br.com.project.modelo.dataTable.PessoaJuridica> getLazyPessoaJuridicaDataTable(){
		try {
			return new br.com.project.dao.JDBC.PessoaJuridicaDAO().getLazyPessoaJuridicaDataTale();
		} catch (SQLException e) {
			return null;
		}
	}

}
