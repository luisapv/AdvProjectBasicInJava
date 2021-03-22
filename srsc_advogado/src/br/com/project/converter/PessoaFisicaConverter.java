package br.com.project.converter;

import javax.faces.convert.FacesConverter;

import br.com.project.modelo.PessoaFisica;

@FacesConverter("pessoaFisicaConverter")
public class PessoaFisicaConverter extends EntityConverter<PessoaFisica>{

	public PessoaFisicaConverter() {
		super(PessoaFisica.class);
	}

}
