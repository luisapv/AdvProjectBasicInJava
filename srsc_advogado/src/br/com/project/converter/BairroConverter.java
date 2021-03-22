package br.com.project.converter;

import javax.faces.convert.FacesConverter;

import br.com.project.modelo.Bairro;

@FacesConverter("bairroConverter")
public class BairroConverter extends EntityConverter<Bairro>{

	public BairroConverter() {
		super(Bairro.class);
	}

}
