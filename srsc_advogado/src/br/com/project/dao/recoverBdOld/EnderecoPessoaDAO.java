package br.com.project.dao.recoverBdOld;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import br.com.project.dao.popula.PopulaCidade;
import br.com.project.modelo.Bairro;
import br.com.project.modelo.Cep;
import br.com.project.modelo.Cidade;
import br.com.project.modelo.Endereco;
import br.com.project.modelo.EnumTipoLogradouro;
import br.com.project.modelo.Login;
import br.com.project.modelo.Logradouro;
import br.com.project.modelo.Pessoa;

public class EnderecoPessoaDAO extends GenericDAO {

	private static final long serialVersionUID = 1L;

	// @Autowired
	private br.com.project.dao.PessoaDAO pessoaServico = new br.com.project.dao.PessoaDAO();
	// @Autowired
	private br.com.project.dao.LoginDAO loginServico = new br.com.project.dao.LoginDAO();
	// @Autowired
	private br.com.project.dao.EnderecoDAO enderecoServico = new br.com.project.dao.EnderecoDAO();
	// @Autowired
	private br.com.project.dao.CepDAO cepServico = new br.com.project.dao.CepDAO();
	// @Autowired
	private br.com.project.dao.CidadeDAO cidadeServico = new br.com.project.dao.CidadeDAO();
	// @Autowired
	private br.com.project.dao.BairroDAO bairroServico = new br.com.project.dao.BairroDAO();
	// @Autowired
	private br.com.project.dao.LogradouroDAO logradouroServico = new br.com.project.dao.LogradouroDAO();

	private PopulaCidade populaCidade = new PopulaCidade();

	private Login loginPadrao = loginServico.obterPorId(new Long(500));

	@SuppressWarnings("static-access")
	public void carregar() {
		try {
			ResultSet result = getFindAll("pessoa", "");

			for (Cidade cidade : populaCidade.listaCidade()) {
				if (cidadeServico.verificarCidade(cidade) == null) {
					cidade.setDataCadastro(new Date());
					cidade.setLogin(loginPadrao);
					cidadeServico.salvar(cidade);
				}
			}

			Cidade cidade;
			Bairro bairro;
			Logradouro logradouro;
			Cep cep;
			Endereco endereco;
			Pessoa pessoa;

			while (result.next()) {

				cidade = null;
				bairro = null;
				logradouro = null;
				cep = null;
				endereco = null;
				pessoa = null;

				cidade = cidadeServico.verificarCidade(getEnumEstado(result.getInt("municipio")),
						result.getString("cidade"));
				if (cidade != null) {
					bairro = bairroServico.verificarBairro(cidade, result.getString("bairro"));
					if (bairro != null) {
						logradouro = logradouroServico.verificarLogradouro(bairro, result.getString("logradouro"));
						if (logradouro != null) {
							cep = cepServico.verificarCep(result.getString("cep"), cidade.getEstado(), cidade, bairro,
									logradouro);
							if (cep != null) {
								pessoa = pessoaServico.obterPorId(new Long(result.getInt("cod_pessoa") * 3 - 2));
								if (pessoa != null) {
									endereco = enderecoServico.verificaPessoa(pessoa);
									if (endereco == null)
										endereco = new Endereco();
									endereco.setCep(cep);
									endereco.setNumero(result.getString("numero"));
									endereco.setComplemento(result.getString("complemento"));
									endereco.setReferencia(result.getString("referencia"));
									endereco.setPessoa(pessoa);
									endereco.setDataCadastro(new Date());
									endereco.setLogin(loginPadrao);

									enderecoServico.salvar(endereco);
								}
							} else {
								cep = new Cep(result.getString("cep"), cidade.getEstado(), cidade, bairro, logradouro,
										new Date(), loginPadrao);
								cepServico.salvar(cep);
								if (result.isLast())
									result.last();
								else
									result.first();
							}
						} else {
							String logradouroStr = result.getString("logradouro");
							EnumTipoLogradouro tipoLogradouro = verificaTipoLogradouro(logradouroStr);

							if (tipoLogradouro != null) {
								if (tipoLogradouro.toString().contains(logradouroStr))
									logradouroStr = logradouroStr.replace(tipoLogradouro.toString(), "");
								else if (tipoLogradouro.getLabel().contains(logradouroStr))
									logradouroStr = logradouroStr.replace(tipoLogradouro.getLabel(), "");

								logradouro = new Logradouro(tipoLogradouro, logradouroStr, bairro, new Date(),
										loginPadrao);
							}
							else
								logradouro = new Logradouro(logradouroStr, bairro, new Date(), loginPadrao);

							logradouroServico.salvarReturn(logradouro);
							if (result.isLast())
								result.last();
							else
								result.first();
						}
					} else {
						bairro = new Bairro(result.getString("bairro"), cidade, new Date(), loginPadrao);
						bairroServico.salvar(bairro);
						if (result.isLast())
							result.last();
						else
							result.beforeFirst();
					}
				} else {
					cidade = new Cidade(result.getString("cidade"), getEnumEstado(result.getInt("municipio")));
					cidade.setDataCadastro(new Date());
					cidade.setLogin(loginPadrao);
					cidadeServico.salvar(cidade);
					if (result.isLast())
						result.last();
					else
						result.first();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private EnumTipoLogradouro verificaTipoLogradouro(String logradouroStr) {
		List<EnumTipoLogradouro> tipoLogradouros = Arrays.asList(EnumTipoLogradouro.values());
		EnumTipoLogradouro retorno = null;
		for (EnumTipoLogradouro tipoLogradouro : tipoLogradouros) {
			if (tipoLogradouro.toString().contains(logradouroStr)) {
				retorno = tipoLogradouro;
				break;
			}
			if (tipoLogradouro.getLabel().contains(logradouroStr)) {
				retorno = tipoLogradouro;
				break;
			}
		}
		return retorno;
	}
}
