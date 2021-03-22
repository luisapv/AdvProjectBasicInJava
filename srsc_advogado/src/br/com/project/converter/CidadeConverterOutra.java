package br.com.project.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.project.modelo.Cidade;

@FacesConverter("cidadeConverterOutra")
public class CidadeConverterOutra implements Converter{

	@Override
	public Object getAsObject(FacesContext context, 
			UIComponent component, String valor) {
		if(valor.equals("") || !valor.contains("#")){
			return null;
		}
				Cidade cidade = new Cidade();
		
		String[] propriedades = valor.split("#");
		if(!propriedades[0].isEmpty()){
			cidade.setId(new Long(propriedades[0]));
		}
		if(!propriedades[1].isEmpty()){
			cidade.setNome(propriedades[1]);
		}
		
		return cidade;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		if(obj == null || !(obj instanceof Cidade)){
			return "";
		}
		
		Cidade cidade = (Cidade) obj;
		
		String id = cidade.getId() == null ? "" : cidade.getId().toString();
		String nome = cidade.getNome() == null ? "" : cidade.getNome();
		
		return id + "#" + nome;
	}

}
