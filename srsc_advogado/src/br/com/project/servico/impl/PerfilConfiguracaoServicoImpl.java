package br.com.project.servico.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.project.modelo.PerfilConfiguracao;
import br.com.project.servico.PerfilConfiguracaoServico;

@Service(value = "perfilConfiguracaoServico")
@Transactional
public class PerfilConfiguracaoServicoImpl implements PerfilConfiguracaoServico {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void salvar(PerfilConfiguracao perfilConfiguracao) {
		entityManager.merge(perfilConfiguracao);
	}

	@Override
	public void excluir(PerfilConfiguracao perfilConfiguracao) {
		perfilConfiguracao = entityManager.merge(perfilConfiguracao);
		entityManager.remove(perfilConfiguracao);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PerfilConfiguracao> listarTodos() {
		return entityManager.createNamedQuery("PerfilConfiguracao.LitarTodos").getResultList();
	}

	@Override
	public PerfilConfiguracao obterPorId(Long id) {
		return entityManager.createQuery("from PerfilConfiguracao where id = :id", PerfilConfiguracao.class).setParameter("id", id)
				.getSingleResult();
	}

}
