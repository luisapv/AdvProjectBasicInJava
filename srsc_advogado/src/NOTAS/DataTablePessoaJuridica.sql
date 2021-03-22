select 
p.id, p.nome, p.apelido, p.documento, p.email,
case
	when e.pessoa <> '' then
concat_ws(' ', l.tipoLogradouro, concat_ws('', l.nome, concat_ws(', ',
case
	when e.numero<>null and e.complemento<>null then concat_ws(', ',e.numero,' - ',e.complemento)
	when e.numero<>null and e.complemento=null then concat_ws(', ',e.numero)
    when e.numero=null and e.complemento<>null then concat_ws(', ',e.complemento)
    else ''
end, concat_ws(', ', b.nome, concat_ws('/', c.nome, concat_ws(' - ',c.estado, cep.cep
))))))
else ''
end as enderecoCompleto,
login.nome as cadastradoPor, p.dataCadastro
from
pessoajuridica as pj
left join pessoa as p on p.id=pj.id
left join endereco as e on p.id=e.pessoa
left join cep on e.cep=cep.id
left join cidade as c on cep.cidade=c.id
left join bairro as b on cep.bairro=b.id
left join logradouro as l on cep.logradouro=l.id
left join login on p.login=login.id