package br.com.project.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.project.modelo.Bairro;
import br.com.project.modelo.Cep;
import br.com.project.modelo.Cidade;
import br.com.project.modelo.EnumEstado;
import br.com.project.modelo.Logradouro;
import br.com.project.servico.BairroServico;
import br.com.project.servico.CepServico;
import br.com.project.servico.CidadeServico;
import br.com.project.servico.LogradouroServico;

@Controller(value = "geralBean")
@Scope(value = "session")
public class GeralBean implements Serializable {

	private static final long serialVersionUID = 2697264978280707957L;
	
	@Autowired
	private CepServico cepServico;
	@Autowired
	private CidadeServico cidadeServico;
	@Autowired
	private BairroServico bairroServico;
	@Autowired
	private LogradouroServico logradouroServico;
	
	@SuppressWarnings("unused")
	private List<EnumEstado> estados = Arrays.asList(EnumEstado.values());

	public List<Cep> getCep(String paramCep) {
//		viaCepServico.validaCepServico(this.getEnderecos().get(0).getCep().getCep().replace("-", ""));
		try {
			return cepServico.obterListPorCep(paramCep);
		} catch (Exception e) {
			return null;
		}
	}

	public List<EnumEstado> getEstados() {
		return Arrays.asList(EnumEstado.values());
	}

	public List<Cidade> getCidadesDoEstado(EnumEstado estado) {
		return cidadeServico.obterCidadesDoEstado(estado);
	}

	public List<Bairro> getBairrosDaCidade(Cidade cidade) {
		return bairroServico.obterBairrosDaCidade(cidade);
	}

	public List<Logradouro> getLogradourosDoBairro(Bairro bairro) {
		return logradouroServico.obterLogradourosDoBairro(bairro);
	}
}
