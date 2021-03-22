package br.com.project.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("cnpjTextConverter")
public class CNPJTextConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uIcomponent, String cnpj) {
		if (cnpj.trim().equals("")) {
			return null;
		} else {
			cnpj = cnpj.replace("/", "");
			cnpj = cnpj.replace("-", "");
			cnpj = cnpj.replace(".", "");
			return cnpj;
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uIcomponent, Object object) {
		return object.toString();
	}
}
