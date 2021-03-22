package br.com.project.converter;

import javax.faces.convert.FacesConverter;

import br.com.project.modelo.Cidade;

@FacesConverter("cidadeConverter")
public class CidadeConverter extends EntityConverter<Cidade>{

	public CidadeConverter() {
		super(Cidade.class);
	}

}
