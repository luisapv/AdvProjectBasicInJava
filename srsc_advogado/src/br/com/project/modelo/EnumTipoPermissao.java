package br.com.project.modelo;

public enum EnumTipoPermissao {

	VER_ADVOGADO(1,"Visualizar - Advogado","",true),
	ADICIONAR_ADVOGADO(2,"Adicionar - Advogado","1",true),
	EDITAR_ADVOGADO(3,"Editar - Advogado","1",true),
	DELETAR_ADVOGADO(4,"Deletar - Advogado","1",true),
	VER_AUTOR(5,"Visualizar - Autor","",true),
	ADICIONAR_AUTOR(6,"Adicionar - Autor","5",true),
	EDITAR_AUTOR(7,"Editar - Autor","5",true),
	DELETAR_AUTOR(8,"Deletar - Autor","5",true),
	VER_BAIRRO(9,"Visualizar - Bairro","",true),
	ADICIONAR_BAIRRO(10,"Adicionar - Bairro","9",true),
	EDITAR_BAIRRO(11,"Editar - Bairro","9",true),
	DELETAR_BAIRRO(12,"Deletar - Bairro","9",true),
	VER_CEP(13,"Visualizar - Cep","",true),
	ADICIONAR_CEP(14,"Adicionar - Cep","13",true),
	EDITAR_CEP(15,"Editar - Cep","13",true),
	DELETAR_CEP(16,"Deletar - Cep","13",true),
	VER_CIDADE(17,"Visualizar - Cidade","",true),
	ADICIONAR_CIDADE(18,"Adicionar - Cidade","17",true),
	EDITAR_CIDADE(19,"Editar - Cidade","17",true),
	DELETAR_CIDADE(20,"Deletar - Cidade","17",true),
	VER_ENDERECO(21,"Visualizar - Endereco","",true),
	ADICIONAR_ENDERECO(22,"Adicionar - Endereco","21",true),
	EDITAR_ENDERECO(23,"Editar - Endereco","21",true),
	DELETAR_ENDERECO(24,"Deletar - Endereco","21",true),
	VER_FASE(25,"Visualizar - Fase","",true),
	ADICIONAR_FASE(26,"Adicionar - Fase","25",true),
	EDITAR_FASE(27,"Editar - Fase","25",true),
	DELETAR_FASE(28,"Deletar - Fase","25",true),
	VER_LOGIN(29,"Visualizar - Login","",true),
	ADICIONAR_LOGIN(30,"Adicionar - Login","29",true),
	EDITAR_LOGIN(31,"Editar - Login","29",true),
	DELETAR_LOGIN(32,"Deletar - Login","29",true),
	VER_LOGRADOURO(33,"Visualizar - Logradouro","",true),
	ADICIONAR_LOGRADOURO(34,"Adicionar - Logradouro","33",true),
	EDITAR_LOGRADOURO(35,"Editar - Logradouro","33",true),
	DELETAR_LOGRADOURO(36,"Deletar - Logradouro","33",true),
	VER_MATERIA(37,"Visualizar - Materia","",true),
	ADICIONAR_MATERIA(38,"Adicionar - Materia","37",true),
	EDITAR_MATERIA(39,"Editar - Materia","37",true),
	DELETAR_MATERIA(40,"Deletar -  Materia","37",true),
	VER_PERMISSAO(41,"Visualizar - Permissao","",true),
	ADICIONAR_PERMISSAO(42,"Adicionar - Permissao","41",true),
	EDITAR_PERMISSAO(43,"Editar - Permissao","41",true),
	DELETAR_PERMISSAO(44,"Deletar - Permissao","41",true),
	VER_PESSOAFISICA(45,"Visualizar - Pessoa F�sica","",true),
	ADICIONAR_PESSOAFISICA(46,"Adicionar - Pessoa F�sica","45",true),
	EDITAR_PESSOAFISICA(47,"Editar - Pessoa F�sica","45",true),
	DELETAR_PESSOAFISICA(48,"Deletar - Pessoa F�sica","45",true),
	VER_PESSOAJURIDICA(49,"Visualizar - Pessoa Juridica","",true),
	ADICIONAR_PESSOAJURIDICA(50,"Adicionar - Pessoa Juridica","49",true),
	EDITAR_PESSOAJURIDICA(51,"Editar - Pessoa Juridica","49",true),
	DELETAR_PESSOAJURIDICA(52,"Deletar - Pessoa Juridica","49",true),
	VER_PROCESSO(53,"Visualizar - Processo","",true),
	ADICIONAR_PROCESSO(54,"Adicionar - Processo","53",true),
	EDITAR_PROCESSO(55,"Editar - Processo","53",true),
	DELETAR_PROCESSO(56,"Deletar - Processo","53",true),
	VER_REU(57,"Visualizar - Reu","",true),
	ADICIONAR_REU(58,"Adicionar - Reu","57",true),
	EDITAR_REU(59,"Editar - Reu","57",true),
	DELETAR_REU(60,"Deletar - Reu","57",true);
	
	private Integer id;
	private String label;
	private String preRequisito;
	private Boolean ativo;
	
	private EnumTipoPermissao(Integer id, String label, String preRequisito, Boolean ativo) {
		this.id = id;
		this.label = label;
		this.preRequisito = preRequisito;
		this.ativo = ativo;
	}

	public Integer getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	public String getPreRequisito() {
		return preRequisito;
	}
	
	public Boolean getAtivo() {
		return ativo;
	}
}
