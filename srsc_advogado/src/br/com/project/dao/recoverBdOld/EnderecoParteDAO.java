package br.com.project.dao.recoverBdOld;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import br.com.project.modelo.Bairro;
import br.com.project.modelo.Cep;
import br.com.project.modelo.Cidade;
import br.com.project.modelo.Endereco;
import br.com.project.modelo.EnumEstado;
import br.com.project.modelo.EnumTipoLogradouro;
import br.com.project.modelo.Login;
import br.com.project.modelo.Logradouro;
import br.com.project.modelo.Pessoa;

public class EnderecoParteDAO extends GenericDAO {

	private static final long serialVersionUID = 1L;

	// private Long idLong;

	// private PreparedStatement preparedStmt;

	// @Autowired
	br.com.project.dao.PessoaDAO pessoaServico = new br.com.project.dao.PessoaDAO();
	// @Autowired
	br.com.project.dao.LoginDAO loginServico = new br.com.project.dao.LoginDAO();
	// @Autowired
	br.com.project.dao.EnderecoDAO enderecoServico = new br.com.project.dao.EnderecoDAO();
	// @Autowired
	br.com.project.dao.CepDAO cepServico = new br.com.project.dao.CepDAO();
	// @Autowired
	br.com.project.dao.CidadeDAO cidadeServico = new br.com.project.dao.CidadeDAO();
	// @Autowired
	br.com.project.dao.BairroDAO bairroServico = new br.com.project.dao.BairroDAO();
	// @Autowired
	br.com.project.dao.LogradouroDAO logradouroServico = new br.com.project.dao.LogradouroDAO();

	private Login loginPadrao = loginServico.obterPorId(new Long(500));

	public void carregar() {
		try {
			ResultSet result1 = getFindAll("pessoa", "");
			while (result1.next())
				verificaCep(result1.getInt("municipio"), result1.getString("cidade"), result1.getString("bairro"),
						result1.getString("logradouro"), result1.getString("cep"));
			ResultSet result2 = getFindAll("empresa", "");
			while (result2.next())
				verificaCep(result2.getInt("municipio"), result2.getString("cidade"), result2.getString("bairro"),
						result2.getString("logradouro"), result2.getString("cep"));

			while (result1.next())
				carregarEnderecoPessoa(result1.getInt("municipio"), result1.getString("cidade"), result1.getString("bairro"),
						result1.getString("logradouro"), result1.getString("cep"), result1.getString("numero"), result1.getString("complemento"), result1.getString("referencia"), pessoaServico.obterPorId(new Long(result1.getInt("cod_pessoa")*3-2)));
			while (result2.next())
				carregarEnderecoPessoa(result2.getInt("municipio"), result2.getString("cidade"), result2.getString("bairro"),
						result2.getString("logradouro"), result2.getString("cep"), result2.getString("numero"), result2.getString("complemento"), result2.getString("referencia"), pessoaServico.obterPorId(new Long(result2.getInt("cod_empresa")*3-1)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void carregarEnderecoPessoa(Integer estadoInt, String cidadeStr, String bairroStr, String logradouroStr,
			String cepStr, String numero, String complemento, String referencia, Pessoa pessoa) throws SQLException {
		Cep idCep = verificaCep(estadoInt, cidadeStr, bairroStr, logradouroStr, cepStr);

		if (idCep != null && pessoa != null) {
			Endereco endereco = new Endereco();
			endereco.setCep(idCep);
			endereco.setPessoa(pessoa);
			endereco.setComplemento(complemento);
			endereco.setNumero(numero);
			endereco.setReferencia(referencia);
			endereco.setDataCadastro(new Date());
			endereco.setLogin(loginPadrao);
			enderecoServico.salvar(endereco);
		}
	}

	protected Endereco carregarEnderecoLogin(Integer estadoInt, String cidadeStr, String bairroStr,
			String logradouroStr, String cepStr, String numero, String complemento, String referencia)
			throws SQLException {
		Cep idCep = verificaCep(estadoInt, cidadeStr, bairroStr, logradouroStr, cepStr);
		Endereco endereco = enderecoServico.verificaEnderecoLogin(idCep);
		if (endereco == null)
			endereco = new Endereco();
		endereco.setCep(idCep);
		endereco.setComplemento(complemento);
		endereco.setNumero(numero);
		endereco.setReferencia(referencia);
		endereco.setDataCadastro(new Date());
		endereco.setLogin(loginPadrao);
		return enderecoServico.salvarReturn(endereco);
	}

	private Cep verificaCep(Integer estadoInt, String cidadeStr, String bairroStr, String logradouroStr, String cepStr)
			throws SQLException {
		EnumEstado estado = getEnumEstado(estadoInt);
		Cidade idCidade = null;
		Bairro idBairro = null;
		Logradouro idLogradouro = null;
		Cep cep = null;
		if (estado != null && cidadeStr != null || !cidadeStr.isEmpty())
			idCidade = verificaCidade(estado, cidadeStr);
		if (idCidade != null && bairroStr != null || !bairroStr.isEmpty())
			idBairro = verificaBairro(idCidade, bairroStr);
		EnumTipoLogradouro tipoLogradouro = verificaTipoLogradouro(logradouroStr);

		if (tipoLogradouro != null) {
			if (tipoLogradouro.toString().contains(logradouroStr)) {
				logradouroStr = logradouroStr.replace(tipoLogradouro.toString(), "");
			} else if (tipoLogradouro.getLabel().contains(logradouroStr)) {
				logradouroStr = logradouroStr.replace(tipoLogradouro.getLabel(), "");
			}
		}

		if (idLogradouro != null && logradouroStr != null || !logradouroStr.isEmpty())
			idLogradouro = verificaLogradouro(idBairro, tipoLogradouro, logradouroStr);

		if (estado != null && idCidade != null && idBairro != null && idLogradouro != null) {
			cep = cepServico.verificarCep(cepStr, estado, idCidade, idBairro, idLogradouro);

			if (cep == null) {
				cep = new Cep();
				cep.setCep(cepStr);
				cep.setEstado(estado);
				cep.setCidade(idCidade);
				cep.setBairro(idBairro);
				cep.setLogradouro(idLogradouro);
				cep.setDataCadastro(new Date());
				cep.setLogin(loginPadrao);
				cep = cepServico.salvarReturn(cep);
			}
		}
		return cep;

		// String sql = "select id, count(cep) as count from cep where estado
		// ='"+estado+"'"
		// +" and cidade='"+idCidade+"'"
		// +" and bairro='"+idBairro+"'"
		// +" and logradouro='"+idLogradouro;
		//
		// if (!verificaExistencia(sql)) {
		// preparedStmt = saveNew("cep",
		// "`id`,`cep`,`cepCorreto`,`estado`,`cidade`,`bairro`,`logradouro`,`login`,`dataCadastro`","?,?,?,?,?,?,?,?,?");
		// preparedStmt.setLong(1, new
		// ControleDeIdSinglonton().getInstacia().acressCep());
		// preparedStmt.setString(2, cepStr);
		// preparedStmt.setBoolean(3, false);
		// preparedStmt.setString(4, estado);
		// preparedStmt.setLong(5, idCidade);
		// preparedStmt.setLong(6, idBairro);
		// preparedStmt.setLong(7, idLogradouro);
		// preparedStmt.setLong(8, new Long(500));
		// preparedStmt.setDate(9, getDate(new java.util.Date()));
		// preparedStmt.execute();
		//
		// if(verificaExistencia(sql)){
		// Long id = this.idLong;
		// this.idLong = null;
		// return id;
		// }
		// else{
		// return null;
		// }
		// }
		// else{
		// Long id = this.idLong;
		// this.idLong = null;
		// return id;
		// }
	}

	private Cidade verificaCidade(EnumEstado estado, String cidadeStr) throws SQLException {
		Cidade cidade = cidadeServico.verificarCidade(estado, cidadeStr);

		if (cidade == null) {
			cidade = new Cidade();
			cidade.setNome(cidadeStr);
			cidade.setEstado(estado);
			cidade.setDataCadastro(new Date());
			cidade.setLogin(loginPadrao);
			cidade = cidadeServico.salvarReturn(cidade);
		}

		return cidade;

		// String sql = "select id, count(nome) as count from cidade where
		// nome='" + cidadeStr + "' and estado='" + estado.toString()
		// + "'";
		// if (!verificaExistencia(sql)) {
		// Cidade cidade = new Cidade();
		// cidade.setNome(cidadeStr);
		// cidade.setEstado(estado);
		// cidade.setDataCadastro(new Date());
		// cidade.setDeletado(false);
		// cidade.setLogin();
		//
		// preparedStmt = saveNew("cidade",
		// "`id`,`dataCadastro`,`deletado`,`estado`,`nome`,`login`",
		// "?,?,?,?,?,?");
		// preparedStmt.setLong(1, new
		// ControleDeIdSinglonton().getInstacia().acressCidade());
		// preparedStmt.setDate(2, getDate(new java.util.Date()));
		// preparedStmt.setBoolean(3, false);
		// preparedStmt.setString(4, estado);
		// preparedStmt.setString(5, cidadeStr);
		// preparedStmt.setLong(6, new Long(500));
		// preparedStmt.execute();
		//
		// return verificaCidade(estado, cidadeStr);
		// } else {
		// Long id = this.idLong;
		// this.idLong = null;
		// return id;
		// }
	}

	private Bairro verificaBairro(Cidade cidade, String bairroStr) throws SQLException {
		Bairro bairro = bairroServico.verificarBairro(cidade, bairroStr);

		if (bairro == null) {
			bairro = new Bairro();
			bairro.setNome(bairroStr);
			bairro.setCidade(cidade);
			bairro.setDataCadastro(new Date());
			bairro.setLogin(loginPadrao);
			bairro = bairroServico.salvarReturn(bairro);
		}

		return bairro;

		// String sql = "select id, count(nome) as count from bairro where
		// nome='" + bairroStr + "' and cidade=" + cidade;
		// if (!verificaExistencia(sql)) {
		// preparedStmt = saveNew("bairro",
		// "`id`,`dataCadastro`,`deletado`,`cidade`,`nome`,`login`",
		// "?,?,?,?,?,?");
		// preparedStmt.setLong(1, new
		// ControleDeIdSinglonton().getInstacia().acressBairro());
		// preparedStmt.setDate(2, getDate(new java.util.Date()));
		// preparedStmt.setBoolean(3, false);
		// preparedStmt.setLong(4, cidade);
		// preparedStmt.setString(5, bairroStr);
		// preparedStmt.setLong(6, new Long(500));
		// preparedStmt.execute();
		//
		// return verificaBairro(cidade, bairroStr);
		// } else {
		// Long id = this.idLong;
		// this.idLong = null;
		// return id;
		// }
	}

	private Logradouro verificaLogradouro(Bairro bairro, EnumTipoLogradouro tipoLogradouro, String logradouroStr)
			throws SQLException {
		Logradouro logradouro = logradouroServico.verificarLogradouro(bairro, logradouroStr);

		if (logradouro == null) {
			logradouro = new Logradouro();
			logradouro.setNome(logradouroStr);
			logradouro.setBairro(bairro);
			logradouro.setDataCadastro(new Date());
			logradouro.setLogin(loginPadrao);
			logradouro = logradouroServico.salvarReturn(logradouro);
		}

		return logradouro;

		// String sql = "select id, count(nome) as count from logradouro where
		// tipoLogradouro='" + tipoLogradouro
		// + "' and nome='" + logradouroStr + "' and bairro=" + bairro;
		// if (!verificaExistencia(sql)) {
		// preparedStmt =
		// saveNew("logradouro","`id`,`dataCadastro`,`nome`,`bairro`,`login`","?,?,?,?,?");
		// preparedStmt.setLong(1, new
		// ControleDeIdSinglonton().getInstacia().acressLogradouro());
		// preparedStmt.setDate(2, getDate(new java.util.Date()));
		// preparedStmt.setString(3, logradouroStr);
		//// preparedStmt.setString(4, tipoLogradouro);
		// preparedStmt.setLong(4, bairro);
		// preparedStmt.setLong(5, new Long(500));
		// preparedStmt.execute();
		//
		// return verificaLogradouro(bairro, tipoLogradouro, logradouroStr);
		// } else {
		// Long id = this.idLong;
		// this.idLong = null;
		// return id;
		// }
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
