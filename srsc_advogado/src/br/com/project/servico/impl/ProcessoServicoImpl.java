package br.com.project.servico.impl;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.project.modelo.Processo;
import br.com.project.servico.ProcessoServico;

@Service(value = "processoServico")
@Transactional
public class ProcessoServicoImpl implements ProcessoServico {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void salvar(Processo processo) {
		entityManager.merge(processo);
	}

	@Override
	public Processo salvarReturn(Processo processo) {
		return entityManager.merge(processo);
	}

	@Override
	public void excluir(Processo processo) {
		processo = entityManager.merge(processo);
		entityManager.remove(processo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Processo> listarTodos() {
		return entityManager.createNamedQuery("Processo.LitarTodos").getResultList();
	}

	@Override
	public Processo obterPorId(Processo processo) {
		return entityManager.createQuery("from Processo where processo = :processo", Processo.class)
				.setParameter("processo", processo.getProcesso()).getSingleResult();
	}

	@Override
	public Processo obterPorId(String processo) {
		return entityManager.createQuery("from Processo where processo = :processo", Processo.class)
				.setParameter("processo", processo).getSingleResult();
	}

	@SuppressWarnings({ "unchecked", "null" })
	@Override
	public List<String> obterJuizes(String juiz) {
		if (juiz != null || !juiz.isEmpty())
			if (juiz.equalsIgnoreCase("*"))
				return (List<String>) entityManager.createQuery("select distinct juizSenteca from Processo").getResultList();
			else
				return (List<String>) entityManager
						.createQuery("select distinct juizSenteca from Processo where lower(juizSenteca) like lower(:juiz)")
						.setParameter("juiz", juiz + "%").getResultList();
		else
			return null;
	}

	@SuppressWarnings({ "unchecked", "null" })
	@Override
	public List<String> obterTrtRelator(String trtRelator) {
		if (trtRelator != null || !trtRelator.isEmpty())
			if (trtRelator.equalsIgnoreCase("*"))
				return (List<String>) entityManager.createQuery("select distinct trtRelator from Processo").getResultList();
			else
				return (List<String>) entityManager
						.createQuery("select distinct trtRelator from Processo where lower(trtRelator) like lower(:relator)")
						.setParameter("relator", trtRelator + "%").getResultList();
		else
			return null;
	}

	@SuppressWarnings({ "unchecked", "null" })
	@Override
	public List<String> obterTstRelator(String tstRelator) {
		if (tstRelator != null || !tstRelator.isEmpty())
			if (tstRelator.equalsIgnoreCase("*"))
				return (List<String>) entityManager.createQuery("select distinct tstRelator from Processo").getResultList();
			else
				return (List<String>) entityManager
						.createQuery("select distinct tstRelator from Processo where lower(tstRelator) like lower(:relator)")
						.setParameter("relator", tstRelator + "%").getResultList();
		else
			return null;
	}

	public List<br.com.project.modelo.dataTable.Processo> getLazyProcessoDataTable(){
		try {
			return new br.com.project.dao.JDBC.ProcessoDAO().getLazyProcessoDataTale();
		} catch (SQLException e) {
			return null;
		}
	}

}
