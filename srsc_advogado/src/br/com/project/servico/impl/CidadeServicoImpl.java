package br.com.project.servico.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.project.modelo.Cidade;
import br.com.project.modelo.EnumEstado;
import br.com.project.servico.CidadeServico;

@Service("cidadeServico")
@Transactional
public class CidadeServicoImpl implements CidadeServico {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void salvar(Cidade cidade) {
		entityManager.merge(cidade);
	}

	@Override
	public Cidade salvarReturn(Cidade cidade) {
		return entityManager.merge(cidade);
	}

	@Override
	public void excluir(Cidade cidade) {
		cidade = entityManager.merge(cidade);
		entityManager.remove(cidade);
	}
	
	@Override
	public List<Cidade> listarTodos() {
		return entityManager.createNamedQuery("Cidade.LitarTodos", Cidade.class).getResultList();
	}

	@Override
	public Cidade obterPorId(Long id) {
		return entityManager.createQuery("from Cidade where id = :id", Cidade.class).setParameter("id", id)
				.getSingleResult();
	}
	
	@Override
	public List<Cidade> obterCidadesDoEstado(EnumEstado estado) {
		return entityManager.createQuery("from Cidade where estado = :uf order by nome", Cidade.class)
				.setParameter("uf", estado).getResultList();
	}

	@Override
	public Cidade verificarCidade(Cidade cidade){
		return entityManager.createQuery("from Cidade where estado = :uf and lower(nome)=lower(:cidade)", Cidade.class).setParameter("uf", cidade.getEstado().toString()).setParameter("cidade", cidade.getNome()).getSingleResult();
	}

	@Override
	public Cidade verificarCidade(EnumEstado estado, String cidadeNome) {
		return entityManager.createQuery("from Cidade where estado = :uf and lower(nome)=lower(:cidade) and  estado = :uf", Cidade.class).setParameter("uf", estado).setParameter("cidade", cidadeNome).getSingleResult();
	}

}
