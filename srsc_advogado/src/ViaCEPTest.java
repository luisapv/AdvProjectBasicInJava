import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import br.com.project.dao.BairroDAO;
import br.com.project.dao.CepDAO;
import br.com.project.dao.CidadeDAO;
import br.com.project.dao.LogradouroDAO;
import br.com.project.modelo.Bairro;
import br.com.project.modelo.Cep;
import br.com.project.modelo.Cidade;
import br.com.project.modelo.EnumEstado;
import br.com.project.modelo.EnumTipoLogradouro;
import br.com.project.modelo.Logradouro;
import br.com.project.servico.viaCep.ViaCEP;
import br.com.project.servico.viaCep.ViaCEPEvents;
import br.com.project.servico.viaCep.ViaCEPException;

public class ViaCEPTest implements ViaCEPEvents {
	
	/**
	 * @param args
	 *            the command line arguments
	 */
	Integer cepInicialReal;
	CidadeDAO cidadeDAO = new CidadeDAO();
	BairroDAO bairroDAO = new BairroDAO();
	LogradouroDAO logradouroDAO = new LogradouroDAO();
	CepDAO cepDAO = new CepDAO();
	
	public static void main(String[] args) {
		try {
			new ViaCEPTest().run();
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() throws NumberFormatException, IOException {
		ViaCEP viaCEP = new ViaCEP(this);
//		Integer.parseInt(leitor("c:\\cep.txt"))
		for (Integer cepInicial = 1683; cepInicial <= 99999999; cepInicial++) {
			String cep = "";
			cepInicialReal = cepInicial;
			if (cepInicial < 10000000) {
				cep = Integer.toString(cepInicial);
				while (cep.length() < 8)
					cep = "0" + cep;
			} else
				cep = Integer.toString(cepInicial);

			try {
				viaCEP.buscar(cep);
			} catch (ViaCEPException ex) {
				System.err.println("Ocorreu um erro na classe " + ex.getClasse() + ": " + ex.getMessage());
			}
		}
	}

	@Override
	public void onCEPSuccess(ViaCEP cep) {
		System.out.println();
		System.out.println("CEP " + cep.getCep() + " encontrado!");
		System.out.println("Logradouro: " + cep.getLogradouro());
		System.out.println("Complemento: " + cep.getComplemento());
		System.out.println("Bairro: " + cep.getBairro());
		System.out.println("Localidade: " + cep.getLocalidade());
		System.out.println("UF: " + cep.getUf());
		System.out.println("Gia: " + cep.getGia());
		System.out.println("Ibge: " + cep.getIbge());
		System.out.println();
		try {
			salvar(cep);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onCEPError(String cep) {
		System.out.println();
		System.out.println("Não foi possível encontrar o CEP " + cep + "!");
		System.out.println();
	}

	public void salvar(ViaCEP cep) throws IOException {
		Cidade cidade = new Cidade();
		cidade.setNome(cep.getLocalidade());
		cidade.setCodigoGia(cep.getGia());
		cidade.setCodigoIbge(cep.getIbge());
		for (EnumEstado estado : Arrays.asList(EnumEstado.values())) {
			if (estado.toString() == cep.getUf()){
				cidade.setEstado(estado);
			}
		}
		if(cidadeDAO.verificarCidade(cidade) == null)
			cidade = cidadeDAO.salvarReturn(cidade);
		else
			cidade = cidadeDAO.verificarCidade(cidade);
		
		Bairro bairro = new Bairro();
		bairro.setNome(cep.getBairro());
		bairro.setCidade(cidade);
		if(bairroDAO.verificarBairro(bairro) == null)
			bairro = bairroDAO.salvarReturn(bairro);
		else
			bairro = bairroDAO.verificarBairro(bairro);
		
		Logradouro logradouro = new Logradouro();
		for (EnumTipoLogradouro tipoLogradouro : Arrays.asList(EnumTipoLogradouro.values())) {
			if (tipoLogradouro.getLabel().contains(cep.getLogradouro())){
				logradouro.setTipoLogradouro(tipoLogradouro);
				logradouro.setNome(cep.getLogradouro().replace(tipoLogradouro.getLabel(), "").trim());
			}
		}
		logradouro.setBairro(bairro);
		logradouro.setComplemento(cep.getComplemento());
		if(logradouroDAO.verificarLogradouro(logradouro) == null)
			logradouro = logradouroDAO.salvarReturn(logradouro);
		else
			logradouro = logradouroDAO.verificarLogradouro(logradouro);
		
		Cep cepNew = new Cep();
		cepNew.setCep(cep.getCep());
		cepNew.setEstado(cidade.getEstado());
		cepNew.setCidade(cidade);
		cepNew.setBairro(bairro);
		cepNew.setLogradouro(logradouro);
		if (cepDAO.obterPorCep(cep.getCep())==null)
			cepDAO.salvar(cepNew);
		else{
			Cep cepOld = cepDAO.obterPorCep(cep.getCep());
			if (cepNew.getCidade().getId() == cepOld.getCidade().getId() &&
					cepNew.getBairro().getId() == cepOld.getBairro().getId() &&
					cepNew.getLogradouro().getId() == cepOld.getLogradouro().getId()){
				cepOld.setCepCorreto(true);
				cepDAO.salvar(cepOld);
			}
			else{
				cepNew.setCepCorreto(true);
				cepDAO.salvar(cepNew);
			}
		}
		
		escritor("c:\\cep.txt", cepInicialReal.toString());
	}
	
	public static String leitor(String path) throws IOException {
        BufferedReader buffRead = new BufferedReader(new FileReader(path));
        String linha = "";
        while (true) {
            if (linha == null) 
                break;
            linha = buffRead.readLine();
        }
        buffRead.close();
        return linha;
    }
 
	public static void escritor(String path, String linha) throws IOException {
        BufferedWriter buffWrite = new BufferedWriter(new FileWriter(path));
        buffWrite.append(linha);
        buffWrite.close();
    }
}
