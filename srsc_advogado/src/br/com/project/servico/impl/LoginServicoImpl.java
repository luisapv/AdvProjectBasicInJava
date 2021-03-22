package br.com.project.servico.impl;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.project.modelo.Login;
import br.com.project.servico.LoginServico;

@Service("loginServico")
@Transactional
public class LoginServicoImpl implements LoginServico {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void salvar(Login login) {
		entityManager.merge(login);
	}

	@Override
	public Login salvarReturn(Login login) {
		return entityManager.merge(login);
	}

	@Override
	public void excluir(Login login) {
		login = entityManager.merge(login);
		entityManager.remove(login);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Login> listarTodos() {
		return entityManager.createNamedQuery("Login.LitarTodos").getResultList();
	}

	@Override
	public Login obterPorId(Long id) {
		return entityManager.createQuery("from Login where id = :id", Login.class).setParameter("id", id)
				.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Login obterLoginPeloLogin(String login) {
		List<Login> usuarios = entityManager.createQuery("from Login where login = :login")
				.setParameter("login", login).getResultList();

		if (usuarios.isEmpty()) {
			return null;
		}

		return usuarios.get(0);
	}

	public List<br.com.project.modelo.dataTable.Login> getLazyLoginDataTable(){
		try {
			return new br.com.project.dao.JDBC.LoginDAO().getLazyLoginDataTale();
		} catch (SQLException e) {
			return null;
		}
	}

}
