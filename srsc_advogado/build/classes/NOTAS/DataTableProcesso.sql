SELECT DISTINCT p.processo, p.arquivo, p.dataArquivo, p.juizSenteca, p.trtTurma, p.trtRelator,
	p.tstTurma, p.tstRelator, l.nome AS cadastradoPor, p.dataCadastro,
	pAutor.nome AS autores,
	pAdvAutor.nome AS advAutor,
	pReu.nome AS reus,
	pAdvReu.nome AS advReu,
	REPLACE(GROUP_CONCAT(f.nome),',','; ') AS fases,
	REPLACE(GROUP_CONCAT(m.nome),',','; ') AS materias
FROM processo AS p
	LEFT JOIN processo_autor AS pa ON p.processo = pa.processo
	LEFT JOIN processo_reu AS pr ON p.processo = pr.processo
	LEFT JOIN processo_advogado_autor AS paa ON p.processo = paa.processo
	LEFT JOIN processo_advogado_reu as par ON par.processo = p.processo
	LEFT JOIN processo_fase AS pf ON pf.processo = p.processo 
	LEFT JOIN processo_materia AS pm ON pm.processo = p.processo
	LEFT JOIN pessoa AS pAutor ON pa.pessoa = pAutor.id
	LEFT JOIN pessoa AS pReu ON pr.pessoa = pReu.id
	LEFT JOIN pessoa AS pAdvAutor ON paa.advogado = pAdvAutor.id
	LEFT JOIN pessoa AS pAdvReu ON par.advogado = pAdvReu.id
	LEFT JOIN fase AS f ON pf.fase = f.id
	LEFT JOIN materia AS m ON pm.materia = m.id
	LEFT JOIN login AS l ON p.login = l.id
GROUP BY p.processo