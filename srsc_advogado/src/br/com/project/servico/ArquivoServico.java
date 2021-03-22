package br.com.project.servico;

import java.io.File;

import br.com.project.modelo.Arquivo;

public interface ArquivoServico {
	public Arquivo inserirArquivoNoSistema(Arquivo arquivo, String diretorio);
	public File obterArquivo(String nomeArquivo, String diretorio);
}
