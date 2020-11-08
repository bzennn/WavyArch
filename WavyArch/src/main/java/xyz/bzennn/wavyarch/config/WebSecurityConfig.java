package xyz.bzennn.wavyarch.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(11);
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("1").password(passwordEncoder().encode("1")).roles("USER");
		auth.inMemoryAuthentication().withUser("admin").password("harnetly365").roles("ADMIN");
		auth.inMemoryAuthentication().withUser("dba").password("harnetly365").roles("DBA");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf()
				.disable()
			.authorizeRequests()
				.antMatchers("/signin", "/signup").anonymous()
				.antMatchers("/", "/resources/**").permitAll()
			.anyRequest()
				.authenticated()
//			.and()
//				.formLogin()
//				.loginPage("/signin");
//				.defaultSuccessUrl("/signin");
//				.permitAll();
			.and()
				.logout()
				.deleteCookies("JSESSIONID")
				.logoutUrl("/signout")
				.permitAll()
				.logoutSuccessUrl("/signin")
			.and()
				.rememberMe()
				.key("verySecretKey");
	}
	
}
