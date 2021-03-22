package br.com.project.converter;

import javax.faces.convert.FacesConverter;

import br.com.project.modelo.Pessoa;

@FacesConverter("pessoaConverter")
public class PessoaConverter extends EntityConverter<Pessoa>{

	public PessoaConverter() {
		super(Pessoa.class);
	}

}
