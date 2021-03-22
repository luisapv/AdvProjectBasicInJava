package br.com.project.servico.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.project.modelo.Fase;
import br.com.project.servico.FaseServico;

@Service(value = "faseServico")
@Transactional
public class FaseServicoImpl implements FaseServico {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void salvar(Fase fase) {
		entityManager.merge(fase);
	}

	@Override
	public Fase salvarReturn(Fase fase) {
		return entityManager.merge(fase);
	}

	@Override
	public void excluir(Fase fase) {
		fase = entityManager.merge(fase);
		entityManager.remove(fase);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Fase> listarTodos() {
		return entityManager.createNamedQuery("Fase.LitarTodos").getResultList();
	}

	@Override
	public Fase obterPorId(Long id) {
		return entityManager.createQuery("from Fase where id = :id", Fase.class).setParameter("id", id)
				.getSingleResult();
	}

	@SuppressWarnings({ "unchecked", "null" })
	@Override
	public List<Fase> obterFase(String fase) {
		if (fase != null || !fase.isEmpty())
			if (fase.equalsIgnoreCase("*"))
				return listarTodos();
			else
				return (List<Fase>) entityManager
						.createQuery("from Fase where lower(nome) like lower(:fase) or sigla like :fase")
						.setParameter("fase", fase + "%").getResultList();
		else
			return null;
	}

	@SuppressWarnings({ "unchecked", "null" })
	@Override
	public List<Fase> obterPorProcesso(String processo) {
		if (processo != null || !processo.isEmpty())
			return (List<Fase>) entityManager
					.createQuery("select f.* from Fase as f left join processo_materia as pf on f.id=pf.materia where processo=:processo")
					.setParameter("processo", processo + "%").getResultList();
		else
			return null;
	}

}
