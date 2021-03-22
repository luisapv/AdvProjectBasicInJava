package br.com.project.dao.recoverBdOld;

public class ControleDeID {

	private Long idEndereco = new Long(0);
	private Long idCep = new Long(0);
	private Long idCidade = new Long(0);
	private Long idBairro = new Long(0);
	private Long idLogradouro = new Long(0);
	
	public Long acressEndereco(){
		return idEndereco++;
	}
	
	public Long acressCep(){
		return idCep++;
	}
	
	public Long acressCidade(){
		return idCidade++;
	}
	
	public Long acressBairro(){
		return idBairro++;
	}
	
	public Long acressLogradouro(){
		return idLogradouro++;
	}
}
