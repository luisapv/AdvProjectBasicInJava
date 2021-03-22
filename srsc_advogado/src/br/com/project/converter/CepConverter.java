package br.com.project.converter;

import javax.faces.convert.FacesConverter;

import br.com.project.modelo.Cep;

@FacesConverter("cepConverter")
public class CepConverter extends EntityConverter<Cep>{

	public CepConverter() {
		super(Cep.class);
	}

}
