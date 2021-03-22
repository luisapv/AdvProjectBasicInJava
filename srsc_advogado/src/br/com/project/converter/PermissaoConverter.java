package br.com.project.converter;

import javax.faces.convert.FacesConverter;

import br.com.project.modelo.Permissao;

@FacesConverter("permissaoConverter")
public class PermissaoConverter extends EntityConverter<Permissao>{

	public PermissaoConverter() {
		super(Permissao.class);
	}

}
