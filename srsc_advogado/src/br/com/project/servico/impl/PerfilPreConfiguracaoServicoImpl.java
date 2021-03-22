package br.com.project.servico.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.project.modelo.EnumPaginas;
import br.com.project.modelo.Login;
import br.com.project.modelo.PerfilPreConfiguracao;
import br.com.project.servico.PerfilPreConfiguracaoServico;

@Service(value = "perfilPreConfiguracaoServico")
@Transactional
public class PerfilPreConfiguracaoServicoImpl implements PerfilPreConfiguracaoServico {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void salvar(PerfilPreConfiguracao perfilPreConfiguracao) {
		entityManager.merge(perfilPreConfiguracao);
	}

	@Override
	public void excluir(PerfilPreConfiguracao perfilPreConfiguracao) {
		perfilPreConfiguracao = entityManager.merge(perfilPreConfiguracao);
		entityManager.remove(perfilPreConfiguracao);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PerfilPreConfiguracao> listarTodos() {
		return entityManager.createNamedQuery("PerfilPreConfiguracao.LitarTodos").getResultList();
	}

	@Override
	public PerfilPreConfiguracao obterPorId(Long id) {
		return entityManager.createQuery("from PerfilPreConfiguracao where id = :id", PerfilPreConfiguracao.class).setParameter("id", id)
				.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PerfilPreConfiguracao> findByLoginPage(Login login, EnumPaginas pagina) {
		return entityManager.createNamedQuery("from PerfilPreConfiguracao where login = :login and pagina = :pagina").setParameter("login", login.getId()).setParameter("pagina", pagina).getResultList();
	}

}
