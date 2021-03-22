package br.com.project.converter;

import javax.faces.convert.FacesConverter;

import br.com.project.modelo.Advogado;

@FacesConverter("advogadoConverter")
public class AdvogadoConverter extends EntityConverter<Advogado>{

	public AdvogadoConverter() {
		super(Advogado.class);
	}

}
