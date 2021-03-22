package br.com.project.servico.impl;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.project.modelo.Bairro;
import br.com.project.modelo.Cidade;
import br.com.project.servico.BairroServico;

@Service("bairroServico")
@Transactional
public class BairroServicoImpl implements BairroServico {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void salvar(Bairro bairro) {
		entityManager.merge(bairro);
	}

	@Override
	public Bairro salvarReturn(Bairro bairro) {
		return entityManager.merge(bairro);
	}

	@Override
	public void excluir(Bairro bairro) {
		bairro = entityManager.merge(bairro);
		entityManager.remove(bairro);
	}

	@Override
	public List<Bairro> listarTodos() {
		return entityManager.createNamedQuery("Bairro.LitarTodos", Bairro.class).getResultList();
	}

	@Override
	public Bairro obterPorId(Long id) {
		return entityManager.createQuery("from Bairro where id = :id", Bairro.class).setParameter("id", id)
				.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Bairro> obterBairrosDaCidade(Cidade cidade) {
		if(cidade.getId()==null)
			return null;
		return (List<Bairro>) entityManager.createQuery("from Bairro where cidade = :cidade order by nome")
				.setParameter("cidade", cidade).getResultList();
	}

	@Override
	public Bairro verificarBairro(Bairro bairro){
		return entityManager.createQuery("from Bairro where cidade = :cidade and lower(nome)=lower(:bairro)", Bairro.class).setParameter("cidade", bairro.getCidade()).setParameter("bairro", bairro.getNome()).getSingleResult();
	}

	@Override
	public Bairro verificarBairro(Cidade cidade, String bairroNome) {
		return entityManager.createQuery("from Bairro where cidade = :cidade and lower(nome)=lower(:bairro)", Bairro.class).setParameter("cidade", cidade).setParameter("bairro", bairroNome).getSingleResult();
	}

	public List<br.com.project.modelo.dataTable.Bairro> getLazyBairroDataTable(){
		try {
			return new br.com.project.dao.JDBC.BairroDAO().getLazyBairroDataTale();
		} catch (SQLException e) {
			return null;
		}
	}
	
}
