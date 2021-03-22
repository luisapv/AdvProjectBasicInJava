package br.com.project.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeradorSenhaAleatoria {
	
	private static String caracteresSenha = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdrfghijklmnopqrstuvwxyz0123456789ãâàáäêèéëîìíïõôòóöûúùüÃÂÀÁÄÊÈÉËÎÌÍÏÕÔÒÓÖÛÙÚÜçÇñÑ´`?!@#$%¨*()[]={}~^.;-_+'ªº¹²³£ ¢¬|";
	
	private static StringBuilder builder;

	public static String gerarSenhaAleatoria(int tamanhoSenha) {
		if(tamanhoSenha<8)
			tamanhoSenha = 8;
		
		builder = new StringBuilder();
		
		do{
			builder.delete(0, builder.length());
			for(int i = 0; i < tamanhoSenha; i++){
				builder.append(caracteresSenha.charAt((int) (Math.random() * 145)));
			}
		}while(!validaPassword(builder.toString()));
		
		return builder.toString();
	}
	
	public static boolean validaPassword(final String password) {
	    Pattern p = Pattern.compile("^(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
	    Matcher m = p.matcher(password);
	    return m.matches();
	}
	
	public static void main(String[] args) {
		System.out.println(gerarSenhaAleatoria(32));
	}
}
