select 
	cep.id,
	cep.cep as cep, 
	c.estado as uf, 
	c.nome as cidade, 
	b.nome as bairro, 
	concat_ws(' ', l.tipoLogradouro, l.nome) as logradouro
from cep as cep
left join cidade as c on cep.cidade=c.id
left join bairro as b on cep.bairro=b.id
left join logradouro as l on cep.logradouro=l.id