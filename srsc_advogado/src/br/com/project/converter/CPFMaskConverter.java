package br.com.project.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("cpfMaskConverter")
public class CPFMaskConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uIcomponent, String cpf) {
		if (cpf.trim().equals("") || cpf.length()<11) {
			return null;
		} else {
			if (cpf.length() == 14)
				return cpf;
			return cpf.substring(0, 3)+"."+cpf.substring(4, 6)+"."+cpf.substring(7, 9)+"-"+cpf.substring(10, 11);
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uIcomponent, Object object) {
		return object.toString();
	}
}
