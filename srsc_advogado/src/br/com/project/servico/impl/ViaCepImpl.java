package br.com.project.servico.impl;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.project.modelo.Bairro;
import br.com.project.modelo.Cep;
import br.com.project.modelo.Cidade;
import br.com.project.modelo.EnumEstado;
import br.com.project.modelo.EnumTipoLogradouro;
import br.com.project.modelo.Logradouro;
import br.com.project.servico.BairroServico;
import br.com.project.servico.CepServico;
import br.com.project.servico.CidadeServico;
import br.com.project.servico.LogradouroServico;
import br.com.project.servico.ViaCepServico;
import br.com.project.servico.viaCep.ViaCEP;
import br.com.project.servico.viaCep.ViaCEPEvents;
import br.com.project.servico.viaCep.ViaCEPException;

@Service(value = "viaCepServico")
@Transactional
public class ViaCepImpl implements ViaCepServico, ViaCEPEvents {

	@Autowired
	private CidadeServico cidadeServico;
	@Autowired
	private BairroServico bairroServico;
	@Autowired
	private LogradouroServico logradouroServico;
	@Autowired
	private CepServico cepServico;

	@Override
	public void onCEPSuccess(ViaCEP cep) {
		Cidade cidade = new Cidade();
		cidade.setNome(cep.getLocalidade());
		cidade.setCodigoGia(cep.getGia());
		cidade.setCodigoIbge(cep.getIbge());
		for (EnumEstado estado : Arrays.asList(EnumEstado.values())) {
			if (estado.toString() == cep.getUf()) {
				cidade.setEstado(estado);
			}
		}
		if (cidadeServico.verificarCidade(cidade) == null)
			cidade = cidadeServico.salvarReturn(cidade);
		else
			cidade = cidadeServico.verificarCidade(cidade);

		Bairro bairro = new Bairro();
		bairro.setNome(cep.getBairro());
		bairro.setCidade(cidade);
		if (bairroServico.verificarBairro(bairro) == null)
			bairro = bairroServico.salvarReturn(bairro);
		else
			bairro = bairroServico.verificarBairro(bairro);

		Logradouro logradouro = new Logradouro();
		for (EnumTipoLogradouro tipoLogradouro : Arrays.asList(EnumTipoLogradouro.values())) {
			if (tipoLogradouro.getLabel().contains(cep.getLogradouro())) {
				logradouro.setTipoLogradouro(tipoLogradouro);
				logradouro.setNome(cep.getLogradouro().replace(tipoLogradouro.getLabel(), "").trim());
			}
		}
		logradouro.setBairro(bairro);
		logradouro.setComplemento(cep.getComplemento());
		if (logradouroServico.verificarLogradouro(logradouro) == null)
			logradouro = logradouroServico.salvarReturn(logradouro);
		else
			logradouro = logradouroServico.verificarLogradouro(logradouro);

		Cep cepNew = new Cep();
		cepNew.setCep(cep.getCep());
		cepNew.setEstado(cidade.getEstado());
		cepNew.setCidade(cidade);
		cepNew.setBairro(bairro);
		cepNew.setLogradouro(logradouro);
		if (cepServico.obterPorCep(cep.getCep()) == null)
			cepServico.salvar(cepNew);
		else {
			Cep cepOld = cepServico.obterPorCep(cep.getCep());
			if (cepNew.getCidade().getId() == cepOld.getCidade().getId()
					&& cepNew.getBairro().getId() == cepOld.getBairro().getId()
					&& cepNew.getLogradouro().getId() == cepOld.getLogradouro().getId()) {
				cepOld.setCepCorreto(true);
				cepServico.salvar(cepOld);
			} else {
				cepNew.setCepCorreto(true);
				cepServico.salvar(cepNew);
			}
		}
	}

	@Override
	public void onCEPError(String cep) {

	}

	@Override
	public void validaCepServico(String cep) {
		ViaCEP viaCEP = new ViaCEP(this);
		try {
			viaCEP.buscar(cep);
		} catch (ViaCEPException ex) {
			System.err.println("Ocorreu um erro na classe " + ex.getClasse() + ": " + ex.getMessage());
		}
	}

}
