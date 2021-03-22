package br.com.project.servico.impl;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.project.modelo.Endereco;
import br.com.project.modelo.Pessoa;
import br.com.project.servico.EnderecoServico;

@Service(value = "enderecoServico")
@Transactional
public class EnderecoServicoImpl implements EnderecoServico {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void salvar(Endereco endereco) {
		entityManager.merge(endereco);
	}

	@Override
	public Endereco salvarReturn(Endereco endereco) {
		return entityManager.merge(endereco);
	}

	@Override
	public void excluir(Endereco endereco) {
		endereco = entityManager.merge(endereco);
		entityManager.remove(endereco);
	}

	@Override
	public void excluirPorPessoa(Pessoa pessoa) {
		entityManager.createQuery("from Endereco where pessoa = :pessoa", Endereco.class).setParameter("pessoa", pessoa);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Endereco> listarTodos() {
		return entityManager.createNamedQuery("Endereco.LitarTodos").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Endereco> obterPorPessoa(Pessoa pessoa) {
		return (List<Endereco>) entityManager.createQuery("from Endereco where pessoa = :pessoa").setParameter("pessoa", pessoa)
				.getResultList();
	}

	@Override
	public Endereco obterPorId(Long id) {
		return entityManager.createQuery("from Endereco where id = :id", Endereco.class).setParameter("id", id)
				.getSingleResult();
	}

	@Override
	public Endereco obterUmEnderecoPorPessoa(Pessoa pessoa) {
		return entityManager.createQuery("from Endereco where pessoa = :pessoa", Endereco.class).setParameter("pessoa", pessoa.getId())
				.getSingleResult();
	}

	public List<br.com.project.modelo.dataTable.Endereco> getLazyEnderecoDataTable(){
		try {
			return new br.com.project.dao.JDBC.EnderecoDAO().getLazyEnderecoDataTale();
		} catch (SQLException e) {
			return null;
		}
	}
}
