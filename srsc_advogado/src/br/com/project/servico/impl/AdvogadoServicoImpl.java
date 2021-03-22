package br.com.project.servico.impl;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.project.modelo.Advogado;
import br.com.project.servico.AdvogadoServico;

@Service(value = "advogadoServico")
@Transactional
public class AdvogadoServicoImpl implements AdvogadoServico {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void salvar(Advogado advogado) {
		entityManager.merge(advogado);
	}

	@Override
	public Advogado salvarReturn(Advogado advogado) {
		return entityManager.merge(advogado);
	}

	@Override
	public void excluir(Advogado advogado) {
		advogado = entityManager.merge(advogado);
		entityManager.remove(advogado);
	}

	@Override
	public List<Advogado> listarTodos() {
		return entityManager.createNamedQuery("Advogado.LitarTodos", Advogado.class).getResultList();
	}

	@Override
	public Advogado obterPorId(Long id) {
		return entityManager.createQuery("from Advogado where id = :id", Advogado.class).setParameter("id", id)
				.getSingleResult();
	}

	@SuppressWarnings({ "unchecked", "null" })
	@Override
	public List<Advogado> obterAdvogado(String advogado) {
		if (advogado != null || !advogado.isEmpty())
			if (advogado.equalsIgnoreCase("*"))
				return listarTodos();
			else
				return (List<Advogado>) entityManager
						.createQuery(
								"from Advogado where lower(nome) like lower(:advogado) or identidade like :advogado")
						.setParameter("advogado", advogado + "%").getResultList();
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Advogado> obterAdvogadoAutorPorProcesso(String processo) {
			return (List<Advogado>) entityManager
					.createQuery(
							"select a.* from Advogado  as a left join processo_advogado_autor as paa on a.id=paa.advogado where paa.processo = :processo")
					.setParameter("processo", processo).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Advogado> obterAdvogadoReuPorProcesso(String processo) {
			return (List<Advogado>) entityManager
					.createQuery(
							"select a.* from Advogado  as a left join processo_advogado_reu as par on a.id=par.advogado where par.processo = :processo")
					.setParameter("processo", processo).getResultList();
	}

	public List<br.com.project.modelo.dataTable.Advogado> getLazyAdvogadoDataTable(){
		try {
			return new br.com.project.dao.JDBC.AdvogadoDAO().getLazyAdvogadoDataTale();
		} catch (SQLException e) {
			return null;
		}
	}
	
}
