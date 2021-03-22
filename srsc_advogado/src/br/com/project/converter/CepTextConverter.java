package br.com.project.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("cepTextConverter")
public class CepTextConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uIcomponent, String cep) {
		if (cep.trim().equals("")) {
			return null;
		} else {
			cep = cep.replace("-", "");
			return cep;
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uIcomponent, Object object) {
		return object.toString();
	}
}
