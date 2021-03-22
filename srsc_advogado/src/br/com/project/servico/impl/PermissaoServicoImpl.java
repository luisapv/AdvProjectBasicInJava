package br.com.project.servico.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.project.modelo.Permissao;
import br.com.project.servico.PermissaoServico;

@Service(value = "permissaoServico")
@Transactional
public class PermissaoServicoImpl implements PermissaoServico {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void salvar(Permissao permissao) {
		entityManager.merge(permissao);
	}

	@Override
	public void excluir(Permissao permissao) {
		permissao = entityManager.merge(permissao);
		entityManager.remove(permissao);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Permissao> listarTodos() {
		return entityManager.createNamedQuery("Permissao.LitarTodos").getResultList();
	}

	@Override
	public Permissao obterPorId(Long id) {
		return entityManager.createQuery("from Permissao where id = :id", Permissao.class).setParameter("id", id)
				.getSingleResult();
	}

}
