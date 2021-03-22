package br.com.project.servico.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.project.modelo.Materia;
import br.com.project.servico.MateriaServico;

@Service(value = "materiaServico")
@Transactional
public class MateriaServicoImpl implements MateriaServico {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void salvar(Materia materia) {
		entityManager.merge(materia);
	}

	@Override
	public Materia salvarReturn(Materia materia) {
		return entityManager.merge(materia);
	}

	@Override
	public void excluir(Materia materia) {
		materia = entityManager.merge(materia);
		entityManager.remove(materia);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Materia> listarTodos() {
		return entityManager.createNamedQuery("Materia.LitarTodos").getResultList();
	}

	@Override
	public Materia obterPorId(Long id) {
		return entityManager.createQuery("from Materia where id = :id", Materia.class).setParameter("id", id)
				.getSingleResult();
	}

	@SuppressWarnings({ "unchecked", "null" })
	@Override
	public List<Materia> obterMateria(String materia) {
		if (materia != null || !materia.isEmpty())
			if (materia.equalsIgnoreCase("*"))
				return listarTodos();
			else
				return (List<Materia>) entityManager
						.createQuery("from Materia where lower(nome) like lower(:materia) or sigla like :materia")
						.setParameter("materia", materia + "%").getResultList();
		else
			return null;
	}

	@SuppressWarnings({ "unchecked", "null" })
	@Override
	public List<Materia> obterPorProcesso(String processo) {
		if (processo != null || !processo.isEmpty())
			return (List<Materia>) entityManager
					.createQuery(
							"select m.* from Materia as m left join processo_materia as pf on m.id=pf.materia where pf.processo=:processo")
					.setParameter("processo", processo + "%").getResultList();
		else
			return null;
	}

}
