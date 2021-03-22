import java.util.List;

import br.com.project.dao.JDBC.LoginDAO;
import br.com.project.modelo.dataTable.Login;

public class TesteGeral {

	private static LoginDAO loginDAO = new LoginDAO();

	public static void main(String[] args) {
		try {
			List<Login> logins = loginDAO.getLazyLoginDataTale();
			for (Login login : logins) {
				System.out.println(login.getNome() + " - " + login.getEnderecoCompleto());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
