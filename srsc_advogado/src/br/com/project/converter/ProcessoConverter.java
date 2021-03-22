package br.com.project.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("processoConverter")
public class ProcessoConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		// TODO Auto-generated method stub
		return null;
	}
//
//	@Override
//	public Object getAsObject(FacesContext context, 
//			UIComponent component, String valor) {
//		if(valor.equals("") || !valor.contains("#")){
//			return null;
//		}
//		
//		Matricula matricula = new Matricula();
//		
//		String[] propriedades = valor.split("#");
//		if(!propriedades[0].isEmpty()){
//			matricula.setId(new Integer(propriedades[0]));
//		}
//		if(!propriedades[1].isEmpty()){
//			Curso curso = new Curso();
//			curso.setNome(propriedades[1]);
//			matricula.setCurso(curso);
//		}
//		if(!propriedades[2].isEmpty()){
//			Aluno aluno = new Aluno();
//			aluno.setNome(propriedades[2]);
//			matricula.setAluno(aluno);
//		}
//		
//		return matricula;
//	}
//
//	@Override
//	public String getAsString(FacesContext context, UIComponent component, Object obj) {
//		if(obj == null || !(obj instanceof Matricula)){
//			return "";
//		}
//		
//		Matricula matricula = (Matricula) obj;
//		
//		String id = matricula.getId() == null ? "" : matricula.getId().toString();
//		String nomeCurso = matricula.getCurso() == null
//				|| matricula.getCurso().getNome() == null ? "" : matricula.getCurso().getNome();
//		String nomeAluno = matricula.getAluno() == null
//				|| matricula.getAluno().getNome() == null ? "" : matricula.getAluno().getNome();
//		
//		return id + "#" + nomeCurso + "#" + nomeAluno;
//	}

}
