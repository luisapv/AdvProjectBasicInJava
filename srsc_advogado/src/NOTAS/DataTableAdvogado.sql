select 
p.id, p.nome, concat(pf.identidade,'/',pf.estadoIdentidade) as oab,
pf.dataExpedicaoIdentidade, login.nome as cadastradoPor, p.dataCadastro
from advogado as a
left join pessoafisica as pf on pf.id=a.id
left join pessoa as p on p.id=pf.id
left join login on p.login=login.id;