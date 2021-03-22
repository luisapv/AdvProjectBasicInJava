package br.com.project.dao.recoverBdOld;

public class AcressId {
	private Long idEndereco;
	private Long idCep;
	private Long idCidade;
	private Long idBairro;
	private Long idLogradouro;

	public Long acrerssEndereco(){
		return idEndereco++;
	}

	public Long acrerssCep(){
		return idCep++;
	}

	public Long acrerssCidade(){
		return idCidade++;
	}

	public Long acrerssBairro(){
		return idBairro++;
	}

	public Long acrerssLogradouro(){
		return idLogradouro++;
	}
}
