package xyz.bzennn.wavyarch.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;

/**
 * Spring Security Authentication utility class
 *
 * @author bzennn
 * @version 1.0
 */
@Component
public class AuthenticationUtils {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;
	
	public Authentication authenticate(HttpServletRequest request, String login, String password) throws Exception {
		UsernamePasswordAuthenticationToken token =
				new UsernamePasswordAuthenticationToken(login, password);
		
		Authentication auth = authManager.authenticate(token);
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(auth);
		HttpSession httpSession = request.getSession(true);
		httpSession.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);	
		
		return auth;
	}
	
	public void rememberMe(HttpServletRequest request, HttpServletResponse response, Authentication auth) {
		persistentTokenBasedRememberMeServices.loginSuccess(request, response, auth);
	}
	
}
