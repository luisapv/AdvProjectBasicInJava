package br.com.project.converter;

import javax.faces.convert.FacesConverter;

import br.com.project.modelo.PerfilConfiguracao;

@FacesConverter("perfilConfiguracaoConverter")
public class PerfilConfiguracaoConverter extends EntityConverter<PerfilConfiguracao>{

	public PerfilConfiguracaoConverter() {
		super(PerfilConfiguracao.class);
	}

}
