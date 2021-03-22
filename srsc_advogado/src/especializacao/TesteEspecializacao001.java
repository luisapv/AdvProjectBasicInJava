package especializacao;

import java.util.Date;

import br.com.project.dao.AdvogadoDAO;
import br.com.project.dao.LoginDAO;
import br.com.project.modelo.Advogado;
import br.com.project.modelo.EnumEstado;

public class TesteEspecializacao001 {

//	@Autowired
//	private static LoginServico loginServico;
//	
//	@Autowired
//	private static AdvogadoServico advogadoServico;

	public static void main(String[] args) {		
		Advogado advogado = new Advogado();
		advogado.setNome("Luis");
		advogado.setDocumento("123.123.123-12");
		advogado.setIdentidade("123.123-1");
		advogado.setEstadoIdentidade(EnumEstado.RJ);
		advogado.setDataExpedicaoIdentidade(new Date());
		advogado.setDataCadastro(new Date());
		advogado.setLogin(new LoginDAO().obterPorId(new Long(1)));

		try{
			new AdvogadoDAO().salvar(advogado);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
//		Advogado advogado = new Advogado();
//		advogado.setNome("Luis");
//		advogado.setDocumento("123.123.123-12");
//		advogado.setIdentidade("12.123.123-1");
//		advogado.setOrgoao("Detran/RJ");
//		advogado.setOab("123.123-1");
//		advogado.setEstadoOAB(EnumEstado.RJ);
//		advogado.setDataExpedicaoOabOab(new Date());
//		advogado.setDataCadastro(new Date());
//		advogado.setLogin(loginServico.obterPorId(new Long(1)));
//		
//		try{
//			advogadoServico.salvar(advogado);
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}
