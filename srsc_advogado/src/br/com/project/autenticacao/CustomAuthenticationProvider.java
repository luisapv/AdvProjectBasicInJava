package br.com.project.autenticacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import br.com.project.modelo.Login;
import br.com.project.servico.LoginServico;


@SuppressWarnings("deprecation")
public class CustomAuthenticationProvider implements AuthenticationProvider{

	@Autowired
	private LoginServico loginServico;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		UsernamePasswordAuthenticationToken userToken = (UsernamePasswordAuthenticationToken) authentication;
		String loginFornecido = userToken.getName();
		String senhaFornecida = (String) userToken.getCredentials();

		verificarPreenchimentoLoginESenha(loginFornecido, senhaFornecida);
		Login details = loginServico.obterLoginPeloLogin(loginFornecido);
		verificarLoginESenha(senhaFornecida, details);
		
		return new UsernamePasswordAuthenticationToken(details, senhaFornecida, details.getAuthorities());
	}

	private void verificarLoginESenha(String senhaFornecida, Login details) {
		if (details == null) {
			throw new BadCredentialsException("Login e/ou senha inválidos");
		}

		
		/*if (!senhaFornecida.equals(details.getPassword())) {
			throw new BadCredentialsException("Login e/ou senha inválidos");
		}*/
		if (!passwordEncoder.encodePassword(senhaFornecida, null).equals(details.getPassword())) {
			throw new BadCredentialsException("Login e/ou senha inválidos");
		}
//		if (!passwordEncoder.encodePassword(senhaFornecida, null).equals(details.getPassword())) {
//			throw new BadCredentialsException(passwordEncoder.encodePassword(senhaFornecida, null).toString());
//		}
	}
	
	private void verificarPreenchimentoLoginESenha(String loginFornecido, String senhaFornecida) {
		if (loginFornecido == null || loginFornecido.trim().equals("")
				|| senhaFornecida == null
				|| senhaFornecida.trim().equals(""))
			throw new BadCredentialsException(
					"Os campos login e senha são obrigatórios");
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean supports(Class authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
