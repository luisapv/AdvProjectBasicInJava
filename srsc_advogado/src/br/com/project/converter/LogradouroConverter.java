package br.com.project.converter;

import javax.faces.convert.FacesConverter;

import br.com.project.modelo.Logradouro;

@FacesConverter("logradouroConverter")
public class LogradouroConverter extends EntityConverter<Logradouro>{

	public LogradouroConverter() {
		super(Logradouro.class);
	}

}
