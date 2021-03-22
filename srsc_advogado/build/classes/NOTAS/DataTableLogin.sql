select 
login.id, login.nome, login.login, login.senha, login.email, login.tema, login.localSave, login.ativo,
case
	when login.endereco <> '' then
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
login.dataCadastro
from
login
left join endereco as e on e.id = login.endereco
left join cep on e.cep=cep.id
left join cidade as c on cep.cidade=c.id
left join bairro as b on cep.bairro=b.id
left join logradouro as l on cep.logradouro=l.id;