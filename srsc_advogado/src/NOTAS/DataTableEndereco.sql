select 
	e.id,
	p.nome as pessoa, 
	p.documento, 
	e.numero, 
	e.complemento,
	cep.cep as cep, 
	c.estado as uf, 
	c.nome as cidade, 
	b.nome as bairro, 
	concat_ws(' ', l.tipoLogradouro, l.nome) as logradouro
from endereco as e
left join pessoa as p on e.pessoa=p.id
left join cep as cep on e.cep=cep.id
left join cidade as c on cep.cidade=c.id
left join bairro as b on cep.bairro=b.id
left join logradouro as l on cep.logradouro=l.id;