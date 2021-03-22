package br.com.project.converter;

import javax.faces.convert.FacesConverter;

import br.com.project.modelo.Materia;

@FacesConverter("materiaConverter")
public class MateriaConverter extends EntityConverter<Materia>{

	public MateriaConverter() {
		super(Materia.class);
	}

}
