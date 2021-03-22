select
	l.id, trim(concat_ws(' ', l.tipoLogradouro, l.nome)) as nome, concat_ws('\\\\',b.nome, concat_ws('\\\\',c.nome,c.estado)) as bairroCidadeUf
from logradouro as l
left join bairro as b on l.bairro=b.id
left join cidade as c on b.cidade=c.id;