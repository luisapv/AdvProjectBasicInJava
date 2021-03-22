package br.com.project.converter;

import javax.faces.convert.FacesConverter;

import br.com.project.modelo.Fase;

@FacesConverter("faseConverter")
public class FaseConverter extends EntityConverter<Fase>{

	public FaseConverter() {
		super(Fase.class);
	}

}
