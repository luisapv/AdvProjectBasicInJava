package br.com.project.converter;

import javax.faces.convert.FacesConverter;

import br.com.project.modelo.PerfilPreConfiguracao;

@FacesConverter("perfilPreConfiguracaoConverter")
public class PerfilPreConfiguracaoConverter extends EntityConverter<PerfilPreConfiguracao>{

	public PerfilPreConfiguracaoConverter() {
		super(PerfilPreConfiguracao.class);
	}

}
