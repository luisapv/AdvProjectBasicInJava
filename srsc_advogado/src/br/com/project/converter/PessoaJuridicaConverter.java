package br.com.project.converter;

import javax.faces.convert.FacesConverter;

import br.com.project.modelo.PessoaJuridica;

@FacesConverter("pessoaJuridicaConverter")
public class PessoaJuridicaConverter extends EntityConverter<PessoaJuridica>{

	public PessoaJuridicaConverter() {
		super(PessoaJuridica.class);
	}

}
