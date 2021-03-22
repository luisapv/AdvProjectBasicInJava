select
	b.id, b.nome, concat_ws('\\\\',c.nome,c.estado) as cidadeUf
from bairro as b
left join cidade as c on b.cidade=c.id