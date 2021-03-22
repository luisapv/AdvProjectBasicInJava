package br.com.project.control.Impl;

import javax.faces.context.FacesContext;

public class SessionController {

	public static void removeViewScopedBean(String beanName) {
		FacesContext.getCurrentInstance().getViewRoot().getViewMap().remove(beanName);
	}

}
