package br.com.project.servico.impl;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.project.modelo.PessoaFisica;
import br.com.project.servico.PessoaFisicaServico;

@Service(value = "pessoaFisicaServico")
@Transactional
public class PessoaFisicaServicoImpl implements PessoaFisicaServico {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void salvar(PessoaFisica pessoaFisica) {
		entityManager.merge(pessoaFisica);
	}

	@Override
	public PessoaFisica salvarReturn(PessoaFisica pessoaFisica) {
		return (PessoaFisica) entityManager.merge(pessoaFisica);
	}

	@Override
	public void excluir(PessoaFisica pessoaFisica) {
		pessoaFisica = entityManager.merge(pessoaFisica);
		entityManager.remove(pessoaFisica);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PessoaFisica> listarTodos() {
		return (List<PessoaFisica>) entityManager.createNamedQuery("PessoaFisica.LitarTodos").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PessoaFisica> getLazyDataModel(int first, int pageSize) {
		return (List<PessoaFisica>) entityManager.createNamedQuery("PessoaFisica.LitarTodos")
				.setFirstResult(first).setMaxResults(pageSize).getResultList();
	}

	@Override
	public Long getMaxResult() {
		return (Long) entityManager.createQuery("select count(*) from PessoaFisica").getSingleResult();
	}

	@Override
	public PessoaFisica obterPorId(Long id) {
		return (PessoaFisica) entityManager.createQuery("from PessoaFisica where id = :id", PessoaFisica.class)
				.setParameter("id", id).getSingleResult();
	}

	@SuppressWarnings({ "null", "unchecked" })
	@Override
	public List<PessoaFisica> obterPessoaFisica(String pessoaFisica) {
		if (pessoaFisica != null || !pessoaFisica.isEmpty())
			if (pessoaFisica.equalsIgnoreCase("*"))
				return listarTodos();
			else
				return (List<PessoaFisica>) entityManager
						.createQuery(
								"from PessoaFisica where lower(nome) like lower(:pessoaFisica) or documento like :pessoaFisica")
						.setParameter("pessoaFisica", pessoaFisica + "%").getResultList();
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> obterProfissoes()  {
		return (List<String>) entityManager.createQuery("select distinct profissao from PessoaFisica where profissao <> null and profissao <> '' order by profissao").getResultList();
	}

	@SuppressWarnings({ "null", "unchecked" })
	@Override
	public List<String> obterProfissao(String profissao) {
		if (profissao != null || !profissao.isEmpty())
			if (profissao.equalsIgnoreCase("*"))
				return (List<String>) entityManager.createQuery("select distinct profissao from PessoaFisica order by profissao").getResultList();
			else
				return (List<String>) entityManager
						.createQuery("select distinct profissao from PessoaFisica where lower(profissao) like lower(:profissao)")
						.setParameter("profissao", profissao + "%").getResultList();
		else
			return null;
	}

	public List<br.com.project.modelo.dataTable.PessoaFisica> getLazyPessoaFisicaDataTable(){
		try {
			return new br.com.project.dao.JDBC.PessoaFisicaDAO().getLazyPessoaFisicaDataTale();
		} catch (SQLException e) {
			return null;
		}
	}
}
