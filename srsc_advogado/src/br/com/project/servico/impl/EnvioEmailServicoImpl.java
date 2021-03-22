package br.com.project.servico.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.project.modelo.Login;
import br.com.project.modelo.Processo;
import br.com.project.servico.EnvioEmailServico;
import br.com.project.servico.ProcessoServico;

@Service
public class EnvioEmailServicoImpl implements EnvioEmailServico{
	
	@SuppressWarnings("unused")
	@Autowired
	private ProcessoServico processoServico;

	@Override
	public void enviarEmail(String assunto, String texto, 
			List<File> anexos, String... destinatarios) {
		//http://www.mballem.com/post/enviando-email-com-a-api-javamail/
		// Url para liberar acesso a aplicativos menos seguros
		// https://www.google.com/settings/security/lesssecureapps
		
		try {
			JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
			mailSender.setHost("smtp.gmail.com");
			mailSender.setPort(587);
			mailSender.setProtocol("smtp");
			mailSender.setUsername("heitor.instrutor.season@gmail.com");
			mailSender.setPassword("academiaweb20142015");
			mailSender.setDefaultEncoding("utf-8");
			
			Properties properties = new Properties();
			properties.setProperty("username", "heitor.instrutor.season@gmail.com");
			properties.setProperty("password", "academiaweb20142015");
			properties.setProperty("mail.smtp.auth", "true");
			properties.setProperty("mail.smtp.starttls.enable", "true");
			properties.setProperty("mail.transport.protocol", "smtp");
			mailSender.setJavaMailProperties(properties);
			
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true);
			helper.setFrom("heitor.instrutor.season@gmail.com");
			helper.setSubject(assunto);
			helper.setText(texto, true);
			
			for (String destinatario : destinatarios) {
				helper.addTo(destinatario);
			}
			
			if(anexos != null && !anexos.isEmpty()){
				for (File anexo : anexos) {
					FileSystemResource attachment = new FileSystemResource(anexo);
					helper.addAttachment(anexo.getName(), attachment);
				}
			}
			
			mailSender.send(msg);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	@Async
	public void enviarEmailCadastroUsuarioLogin(Login login, String senha) {
		String assunto = "Cadastro de Usuário";
		String texto = pegarHtmlEmail(
				"resources/email_cadastro_usuario_professor.html");
		
		texto = texto.replace("{professor.nome}", login.getNome());
		texto = texto.replace("{professor.login}", login.getLogin());
		texto = texto.replace("{professor.senha}", senha);
		
		enviarEmail(assunto, texto, null, login.getEmail());
	}
	
	@SuppressWarnings("unused")
	@Override
	@Async
	public void enviarEmailCorrecaoProcesso(Processo processo){
//		Processo processoBanco = processoServico
//				.obterPorId(processo.getProcesso());
//		String assunto = "Aviso de correção da avaliação do ";
//		String texto = pegarHtmlEmail("resources/email_correcao_avaliacao.html");
//		texto = texto.replace("{aluno}", processoBanco);
//		texto = texto.replace("{bimestre}", processo);
//		texto = texto.replace("{ano}", processo);
		
//		File anexo = processo.getArquivo().getFile();
		List<File> anexos = new ArrayList<File>();
//		anexos.add(anexo);
		
//		enviarEmail(assunto, texto, anexos, processoBanco);
		
		
	}

	private String pegarHtmlEmail(String url) {
		InputStream is = getClass().getResourceAsStream(url);
		BufferedInputStream bis = new BufferedInputStream(is);
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		int result;
		try {
			result = bis.read();
			while (result != -1) {
				byte b = (byte) result;
				buf.write(b);
				result = bis.read();
			}
			return buf.toString("UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

}