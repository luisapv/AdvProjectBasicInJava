package br.com.project.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

public class UtilWeb {
    /* Redireciona a pagina atual para outra passada
      como parametro
     */
    public static String irParaURL(String url){
        String retorno = null;
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        try {
           response.sendRedirect(url);
           FacesContext.getCurrentInstance().responseComplete();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return retorno;
    }
}
