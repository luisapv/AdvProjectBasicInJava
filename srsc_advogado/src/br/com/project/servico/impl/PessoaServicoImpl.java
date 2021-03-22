package br.com.project.servico.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.project.modelo.Pessoa;
import br.com.project.servico.PessoaServico;

@Service(value = "pessoaServico")
@Transactional
public class PessoaServicoImpl implements PessoaServico {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void salvar(Pessoa pessoa) {
		entityManager.merge(pessoa);
	}

	@Override
	public void excluir(Pessoa pessoa) {
		pessoa = entityManager.merge(pessoa);
		entityManager.remove(pessoa);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pessoa> listarTodos() {
		return entityManager.createNamedQuery("Pessoa.LitarTodos").getResultList();
	}

	@Override
	public Pessoa obterPorId(Long id) {
		return entityManager.createQuery("from Pessoa where id = :id", Pessoa.class).setParameter("id", id)
				.getSingleResult();
	}

	@SuppressWarnings({ "unchecked", "null" })
	@Override
	public List<Pessoa> obterPessoa(String pessoa) {
		if (pessoa != null || !pessoa.isEmpty())
			if (pessoa.equalsIgnoreCase("*"))
				return listarTodos();
			else
				return (List<Pessoa>) entityManager
						.createQuery(
								"from Pessoa where lower(nome) like lower(:pessoa) or documento like :pessoa")
						.setParameter("pessoa", pessoa + "%").getResultList();
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pessoa> obterPessoaAutorPorProcesso(String processo) {
				return (List<Pessoa>) entityManager
						.createQuery(
								"select p.* from Pessoa as p left join processo_autor as pa on p.id=pa.pessoa where pa.processo=:processo")
						.setParameter("processo", processo).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pessoa> obterPessoaReuPorProcesso(String processo) {
				return (List<Pessoa>) entityManager
						.createQuery(
								"select p.* from Pessoa as p left join processo_reu as pr on p.id=pr.pessoa where pr.processo=:processo")
						.setParameter("processo", processo).getResultList();
	}
}
