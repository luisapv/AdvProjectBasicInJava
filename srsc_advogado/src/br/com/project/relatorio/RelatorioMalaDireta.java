package br.com.project.relatorio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.project.modelo.Login;
import br.com.project.modelo.Pessoa;

public class RelatorioMalaDireta {

	public void gerarPDF(List<Pessoa> pessoas, Login login) {
		File file = new File("E:/srsc_advogado/arquivos/");
		if (!file.exists())
			file.getParentFile().mkdirs();
		file = new File("E:/srsc_advogado/arquivos/Mala_Direta.pdf");
		criarRelatorio(pessoas, login, file);
	}

	public void criarRelatorio(List<Pessoa> pessoas, Login login, File destino) {
		// Variaveis de Tamanho
		// Margem Esquerda (mm)
		Float margemEsquerda = (float) 3.95;
		// Margem Direita (mm)
		Float margemDireita = (float) 3.95;
		// Margem Superior (mm)
		Float margemTopo = (float) 12.7;
		// Margem Inferior (mm)
		Float margemFooter = (float) 12.7;
		// Largura da Etiqueta (mm)
		Float larguraEtiqueta = (float) 101.59;
		// Altura da Etiqueta (mm)
		Float alturaEtiqueta = (float) 25.4;
		// Espaço horizontal entre as Etiquetas (mm)
		// Float espacoHorizontalEtiqueta = (float) 0;
		// Espaço vertical entre as Etiquetas (mm)
		// Float espacoVerticalEtiqueta = (float) 4.76;
		Integer coluna = 0;
		Integer linha = 0;

		// Rectangle envelope = new Rectangle((float) 2215.9, (float) 2279.4);
		Document document = new Document(PageSize.A4, margemEsquerda, margemDireita, margemTopo, margemFooter);

		try {
			PdfWriter.getInstance(document, new FileOutputStream(destino));
			PdfPTable table = new PdfPTable(2);
			Paragraph conteudo = new Paragraph();
			for (Pessoa pessoa : pessoas) {

				if (linha > 20) {
					linha = 0;
					conteudo.add(table);
				}

				if (linha == 0) {
					linha = 1;
					document.add(conteudo);
					document.newPage();
					conteudo = new Paragraph();
					table = new PdfPTable(2);
					table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
					table.getDefaultCell().setVerticalAlignment(Element.ALIGN_CENTER);
					table.getDefaultCell().setBorderWidth(larguraEtiqueta);
					table.getDefaultCell().setFixedHeight(alturaEtiqueta);
				}

				if (coluna > 2)
					coluna = 1;
				if (pessoa.getEnderecos() != null)
					while (coluna <= 2) {
						coluna++;

						if (coluna == 1) {
							table.addCell("DESTINATARIO:" + pessoa.getNome() + "\n" + pessoa.getEnderecoCompleto());
						} else {
							table.addCell(
									"REMETENTE:" + login.getNome() + "\n" + login.getEndereco().getEnderecoCompleto());
						}
					}
				linha++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {
			document.close();
		}
	}
}
