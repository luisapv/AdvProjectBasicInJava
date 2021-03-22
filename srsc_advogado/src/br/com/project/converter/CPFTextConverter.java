package br.com.project.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("cpfTextConverter")
public class CPFTextConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uIcomponent, String cpf) {
		if (cpf.trim().equals("")) {
			return null;
		} else {
			cpf = cpf.replace("-", "");
			cpf = cpf.replace(".", "");
			return cpf;
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uIcomponent, Object object) {
		return object.toString();
	}
}
