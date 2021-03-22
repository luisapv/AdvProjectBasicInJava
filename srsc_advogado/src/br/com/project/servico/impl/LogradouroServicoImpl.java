package br.com.project.servico.impl;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.project.modelo.Bairro;
import br.com.project.modelo.Logradouro;
import br.com.project.servico.LogradouroServico;

@Service("logradouroServico")
@Transactional
public class LogradouroServicoImpl implements LogradouroServico {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void salvar(Logradouro logradouro) {
		entityManager.merge(logradouro);
	}

	@Override
	public Logradouro salvarReturn(Logradouro logradouro) {
		return entityManager.merge(logradouro);
	}

	@Override
	public void excluir(Logradouro logradouro) {
		logradouro = entityManager.merge(logradouro);
		entityManager.remove(logradouro);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Logradouro> listarTodos() {
		return entityManager.createNamedQuery("Logradouro.LitarTodos").getResultList();
	}

	@Override
	public Logradouro obterPorId(Long id) {
		return entityManager.createQuery("from Logradouro where id = :id", Logradouro.class).setParameter("id", id)
				.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Logradouro> obterLogradourosDoBairro(Bairro bairro) {
		if(bairro.getId()==null)
			return null;
		return (List<Logradouro>) entityManager.createQuery("from Logradouro where bairro = :bairro order by nome")
				.setParameter("bairro", bairro).getResultList();
	}

	@Override
	public Logradouro verificarLogradouro(Logradouro logradouro){
		return entityManager.createQuery("from Logradouro where bairro = :bairro and lower(nome)=lower(:bairro)", Logradouro.class).setParameter("bairro", logradouro.getBairro()).setParameter("bairro", logradouro.getNome()).getSingleResult();
	}

	@Override
	public Logradouro verificarLogradouro(Bairro bairro, String logradouroNome) {
		return entityManager.createQuery("from Logradouro where bairro = :bairro and lower(nome)=lower(:logradouro)", Logradouro.class).setParameter("bairro", bairro).setParameter("logradouro", logradouroNome).getSingleResult();
	}

	public List<br.com.project.modelo.dataTable.Logradouro> getLazyLogradouroDataTable(){
		try {
			return new br.com.project.dao.JDBC.LogradouroDAO().getLazyLogradouroDataTale();
		} catch (SQLException e) {
			return null;
		}
	}
	
}
