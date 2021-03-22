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
import br.com.project.modelo.EnumEstado;
import br.com.project.modelo.EnumTipoLogradouro;
import br.com.project.modelo.Login;
import br.com.project.modelo.Logradouro;
import br.com.project.modelo.Pessoa;

@SuppressWarnings("unused")
public class EnderecoCompletoDAO extends GenericDAO {

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

	private Login loginPadrao = loginServico.obterPorId(new Long(500));

	public void carregar() {
		ResultSet result;

		Integer estadoStr;
		String cidadeStr;
		String bairroStr;
		String logradouroStr;
		String cepStr;

		EnumEstado estado;
		Cidade cidade;
		Bairro bairro;
		Logradouro logradouro;
		EnumTipoLogradouro tipoLogradouro;
		Cep cep;
		Pessoa pessoa;
		Endereco endereco;
		try {

			// for (Cidade cidadeExt : PopulaCidade.listaCidade()) {
			// cidade = cidadeServico.verificarCidade(cidadeExt.getEstado(),
			// cidadeExt.getNome());
			// if (cidade == null) {
			// cidadeExt.setDataCadastro(new Date());
			// cidadeExt.setLogin(loginPadrao);
			// cidadeExt.setDeletado(false);
			// cidadeServico.salvar(cidadeExt);
			// }
			// }

			// CADASTRO CIDADE PESSOA
			result = getSql("SELECT DISTINCT p.municipio, p.cidade FROM pessoa AS p");
			while (result.next()) {
				estadoStr = result.getInt("municipio");
				estado = getEnumEstado(estadoStr);
				cidadeStr = result.getString("cidade");
				cidade = cidadeServico.verificarCidade(estado, cidadeStr);
				if (cidade == null && (cidadeStr != "" && estado != null)) {
					cidade = new Cidade(cidadeStr, estado);
					cidadeServico.salvar(cidade);
				}
			}

			// CADASTRO CIDADE EMPRESA
			result = getSql("SELECT DISTINCT e.municipio, e.cidade FROM empresa AS e");
			while (result.next()) {
				estadoStr = result.getInt("municipio");
				estado = getEnumEstado(estadoStr);
				cidadeStr = result.getString("cidade");
				cidade = cidadeServico.verificarCidade(estado, cidadeStr);
				if (cidade == null && (cidadeStr != "" && estado != null)) {
					cidade = new Cidade(cidadeStr, estado, new Date(), loginPadrao, false);
					cidadeServico.salvar(cidade);
				}
			}

			// CADASTRO BAIRRO PESSOA
			result = getSql("SELECT DISTINCT p.municipio, p.cidade, p.bairro FROM pessoa AS p");
			while (result.next()) {
				estadoStr = result.getInt("municipio");
				estado = getEnumEstado(estadoStr);
				cidadeStr = result.getString("cidade");
				bairroStr = result.getString("bairro");
				cidade = cidadeServico.verificarCidade(estado, cidadeStr);
				bairro = bairroServico.verificarBairro(cidade, bairroStr);
				if (bairro == null && (bairroStr != "" && cidade != null)) {
					bairro = new Bairro(bairroStr, cidade, new Date(), loginPadrao, false);
					bairroServico.salvar(bairro);
				}
			}

			// CADASTRO BAIRRO EMPRESA
			result = getSql("SELECT DISTINCT e.municipio, e.cidade, e.bairro FROM empresa AS e");
			while (result.next()) {
				estadoStr = result.getInt("municipio");
				estado = getEnumEstado(estadoStr);
				cidadeStr = result.getString("cidade");
				bairroStr = result.getString("bairro");
				cidade = cidadeServico.verificarCidade(estado, cidadeStr);
				bairro = bairroServico.verificarBairro(cidade, bairroStr);
				if (bairro == null && (bairroStr != "" && cidade != null)) {
					bairro = new Bairro(bairroStr, cidade, new Date(), loginPadrao, false);
					bairroServico.salvar(bairro);
				}
			}

			// CADASTRO LOGRADOURO PESSOA
			result = getSql("SELECT DISTINCT p.municipio, p.cidade, p.bairro, p.logradouro FROM pessoa AS p");
			while (result.next()) {
				estadoStr = result.getInt("municipio");
				estado = getEnumEstado(estadoStr);
				cidadeStr = result.getString("cidade");
				bairroStr = result.getString("bairro");
				logradouroStr = result.getString("logradouro");
				cidade = cidadeServico.verificarCidade(estado, cidadeStr);
				bairro = bairroServico.verificarBairro(cidade, bairroStr);

				logradouro = logradouroServico.verificarLogradouro(bairro, logradouroStr);
				if (logradouro == null && (logradouroStr != "" && bairro != null)) {
					tipoLogradouro = verificaTipoLogradouro(logradouroStr);

					if (tipoLogradouro != null) {
						if (tipoLogradouro.toString().contains(logradouroStr))
							logradouroStr = logradouroStr.replace(tipoLogradouro.toString(), "");
						else if (tipoLogradouro.getLabel().contains(logradouroStr))
							logradouroStr = logradouroStr.replace(tipoLogradouro.getLabel(), "");

						logradouro = new Logradouro(tipoLogradouro, logradouroStr, bairro, new Date(), loginPadrao);
					} else
						logradouro = new Logradouro(logradouroStr, bairro, new Date(), loginPadrao);

					logradouroServico.salvar(logradouro);
				}
			}

			// CADASTRO LOGRADOURO EMPRESA
			result = getSql("SELECT DISTINCT e.municipio, e.cidade, e.bairro, e.logradouro FROM empresa AS e");
			while (result.next()) {
				estadoStr = result.getInt("municipio");
				estado = getEnumEstado(estadoStr);
				cidadeStr = result.getString("cidade");
				bairroStr = result.getString("bairro");
				logradouroStr = result.getString("logradouro");
				cidade = cidadeServico.verificarCidade(estado, cidadeStr);
				bairro = bairroServico.verificarBairro(cidade, bairroStr);

				logradouro = logradouroServico.verificarLogradouro(bairro, logradouroStr);
				if (logradouro == null && (logradouroStr != "" && bairro != null)) {
					tipoLogradouro = verificaTipoLogradouro(logradouroStr);

					if (tipoLogradouro != null) {
						if (tipoLogradouro.toString().contains(logradouroStr))
							logradouroStr = logradouroStr.replace(tipoLogradouro.toString(), "");
						else if (tipoLogradouro.getLabel().contains(logradouroStr))
							logradouroStr = logradouroStr.replace(tipoLogradouro.getLabel(), "");

						logradouro = new Logradouro(tipoLogradouro, logradouroStr, bairro, new Date(), loginPadrao);
					} else
						logradouro = new Logradouro(logradouroStr, bairro, new Date(), loginPadrao);

					logradouroServico.salvar(logradouro);
				}
			}

			// CADASTRO CEP PESSOA
			result = getSql("SELECT DISTINCT p.municipio, p.cidade, p.bairro, p.logradouro, p.cep FROM pessoa AS p");
			while (result.next()) {
				estadoStr = result.getInt("municipio");
				estado = getEnumEstado(estadoStr);
				cidadeStr = result.getString("cidade");
				bairroStr = result.getString("bairro");
				logradouroStr = result.getString("logradouro");
				cepStr = result.getString("cep");
				cidade = cidadeServico.verificarCidade(estado, cidadeStr);
				bairro = bairroServico.verificarBairro(cidade, bairroStr);

				tipoLogradouro = verificaTipoLogradouro(logradouroStr);

				if (tipoLogradouro != null) {
					if (tipoLogradouro.toString().contains(logradouroStr))
						logradouroStr = logradouroStr.replace(tipoLogradouro.toString(), "");
					else if (tipoLogradouro.getLabel().contains(logradouroStr))
						logradouroStr = logradouroStr.replace(tipoLogradouro.getLabel(), "");

					logradouro = logradouroServico.verificarLogradouro(bairro, logradouroStr, tipoLogradouro);
				} else
					logradouro = logradouroServico.verificarLogradouro(bairro, logradouroStr);

				cep = cepServico.verificarCep(cepStr, estado, cidade, bairro, logradouro);
				if (cep == null && (cepStr != "" && estado != null && cidade != null && bairro != null && logradouro != null)) {
					cep = new Cep(cepStr, estado, cidade, bairro, logradouro, new Date(), loginPadrao);
					cepServico.salvar(cep);
				}
			}

			// CADASTRO CEP EMPRESA
			result = getSql("SELECT DISTINCT e.municipio, e.cidade, e.bairro, e.logradouro, e.cep FROM empresa AS e");
			while (result.next()) {
				estadoStr = result.getInt("municipio");
				estado = getEnumEstado(estadoStr);
				cidadeStr = result.getString("cidade");
				bairroStr = result.getString("bairro");
				logradouroStr = result.getString("logradouro");
				cepStr = result.getString("cep");
				cidade = cidadeServico.verificarCidade(estado, cidadeStr);
				bairro = bairroServico.verificarBairro(cidade, bairroStr);

				tipoLogradouro = verificaTipoLogradouro(logradouroStr);

				if (tipoLogradouro != null) {
					if (tipoLogradouro.toString().contains(logradouroStr))
						logradouroStr = logradouroStr.replace(tipoLogradouro.toString(), "");
					else if (tipoLogradouro.getLabel().contains(logradouroStr))
						logradouroStr = logradouroStr.replace(tipoLogradouro.getLabel(), "");

					logradouro = logradouroServico.verificarLogradouro(bairro, logradouroStr, tipoLogradouro);
				} else
					logradouro = logradouroServico.verificarLogradouro(bairro, logradouroStr);

				cep = cepServico.verificarCep(cepStr, estado, cidade, bairro, logradouro);
				if (cep == null && (cepStr != "" && estado != null && cidade != null && bairro != null && logradouro != null)) {
					cep = new Cep(cepStr, estado, cidade, bairro, logradouro, new Date(), loginPadrao);
					cepServico.salvar(cep);
				}
			}

			// CADASTRO ENDERECO PESSOA
			result = getSql(
					"SELECT DISTINCT p.municipio, p.cidade, p.bairro, p.logradouro, p.cep, p.numero, p.complemento, p.referencia, p.cod_pessoa FROM pessoa AS p");
			while (result.next()) {
				pessoa = pessoaServico.obterPorId(new Long(result.getInt("cod_pessoa") * 3 - 2));
				endereco = enderecoServico.verificaPessoa(pessoa);
				if (endereco == null) {
					endereco = new Endereco();
					endereco.setPessoa(pessoa);
				}
				estadoStr = result.getInt("municipio");
				estado = getEnumEstado(estadoStr);
				cidadeStr = result.getString("cidade");
				bairroStr = result.getString("bairro");
				logradouroStr = result.getString("logradouro");
				cepStr = result.getString("cep");

				cidade = cidadeServico.verificarCidade(estado, cidadeStr);
				bairro = bairroServico.verificarBairro(cidade, bairroStr);

				tipoLogradouro = verificaTipoLogradouro(logradouroStr);

				if (tipoLogradouro != null) {
					if (tipoLogradouro.toString().contains(logradouroStr))
						logradouroStr = logradouroStr.replace(tipoLogradouro.toString(), "");
					else if (tipoLogradouro.getLabel().contains(logradouroStr))
						logradouroStr = logradouroStr.replace(tipoLogradouro.getLabel(), "");

					logradouro = logradouroServico.verificarLogradouro(bairro, logradouroStr, tipoLogradouro);
				} else
					logradouro = logradouroServico.verificarLogradouro(bairro, logradouroStr);

				cep = cepServico.verificarCep(cepStr, estado, cidade, bairro, logradouro);

				if (result.getString("numero") != null && !result.getString("numero").isEmpty())
					endereco.setNumero(result.getString("numero"));
				if (result.getString("complemento") != null && !result.getString("complemento").isEmpty())
					endereco.setComplemento(result.getString("complemento"));
				if (result.getString("referencia") != null && !result.getString("referencia").isEmpty())
					endereco.setReferencia(result.getString("referencia"));
				if (cep != null && (cepStr != "" && estado != null && cidade != null && bairro != null && logradouro != null)) {
					endereco.setCep(cep);
					endereco.setDataCadastro(new Date());
					endereco.setLogin(loginPadrao);
					enderecoServico.salvar(endereco);
				}
				else
					System.err.println("SEM CEP!");
			}

			// CADASTRO ENDERECO EMPRESA
			result = getSql(
					"SELECT DISTINCT e.municipio, e.cidade, e.bairro, e.logradouro, e.cep, e.numero, e.complemento, e.referencia, e.cod_empresa FROM empresa AS e");
			while (result.next()) {
				pessoa = pessoaServico.obterPorId(new Long(result.getInt("cod_empresa") * 3 - 1));
				endereco = enderecoServico.verificaPessoa(pessoa);
				if (endereco == null){
					endereco = new Endereco();
					endereco.setPessoa(pessoa);
				}
				estadoStr = result.getInt("municipio");
				estado = getEnumEstado(estadoStr);
				cidadeStr = result.getString("cidade");
				bairroStr = result.getString("bairro");
				logradouroStr = result.getString("logradouro");
				cepStr = result.getString("cep");

				cidade = cidadeServico.verificarCidade(estado, cidadeStr);
				bairro = bairroServico.verificarBairro(cidade, bairroStr);

				tipoLogradouro = verificaTipoLogradouro(logradouroStr);

				if (tipoLogradouro != null) {
					if (tipoLogradouro.toString().contains(logradouroStr))
						logradouroStr = logradouroStr.replace(tipoLogradouro.toString(), "");
					else if (tipoLogradouro.getLabel().contains(logradouroStr))
						logradouroStr = logradouroStr.replace(tipoLogradouro.getLabel(), "");

					logradouro = logradouroServico.verificarLogradouro(bairro, logradouroStr, tipoLogradouro);
				} else
					logradouro = logradouroServico.verificarLogradouro(bairro, logradouroStr);

				cep = cepServico.verificarCep(cepStr, estado, cidade, bairro, logradouro);

				if (result.getString("numero") != null && !result.getString("numero").isEmpty())
					endereco.setNumero(result.getString("numero"));
				if (result.getString("complemento") != null && !result.getString("complemento").isEmpty())
					endereco.setComplemento(result.getString("complemento"));
				if (result.getString("referencia") != null && !result.getString("referencia").isEmpty())
					endereco.setReferencia(result.getString("referencia"));
				if (cep != null && (cepStr != "" && estado != null && cidade != null && bairro != null && logradouro != null)){
					endereco.setCep(cep);
					endereco.setDataCadastro(new Date());
					endereco.setLogin(loginPadrao);
					enderecoServico.salvar(endereco);
				}
				else
					System.err.println("SEM CEP!");
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
