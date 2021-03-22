package br.com.project.converter;

import javax.faces.convert.FacesConverter;

import br.com.project.modelo.Login;

@FacesConverter("loginConverter")
public class LoginConverter extends EntityConverter<Login>{

	public LoginConverter() {
		super(Login.class);
	}

}
