package br.com.project.servico;

import java.io.File;
import java.util.List;

import br.com.project.modelo.Login;
import br.com.project.modelo.Processo;

public interface EnvioEmailServico {

	public void enviarEmail(String assunto, String texto, 
			List<File> anexos, String... destinatarios);
	public void enviarEmailCadastroUsuarioLogin(
			Login login, String senha);
	public void enviarEmailCorrecaoProcesso(Processo processo);
}
