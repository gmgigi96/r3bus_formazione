package org.bitbucket.r3bus;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final String query = "SELECT username, password, auth FROM utenti WHERE username = ?";
	
	@Qualifier("dataSource")
	@Autowired
	private DataSource dataSource;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
			.passwordEncoder(new BCryptPasswordEncoder())
			.usersByUsernameQuery(query)
			.authoritiesByUsernameQuery(query);		
	}
	
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/favicon.ico", "/login*", "/error", "/js/**", "/css/**", "/images/**");
//	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
			.passwordEncoder(bCryptPasswordEncoder())
			.usersByUsernameQuery(query)
			.authoritiesByUsernameQuery(query);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/favicon.ico", "/login*", "/error", "/js/**", "/css/**", "/images/**").permitAll()
			.antMatchers("/responsabile/**").hasAuthority("RESPONSABILE")
			.antMatchers("/direttore/**").hasAuthority("DIRETTORE")
			.antMatchers("/organizzatore/**").hasAuthority("ORGANIZZATORE")
			.antMatchers("/**").authenticated()
			.anyRequest().permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/loginSuccess")
			.permitAll()
			.failureUrl("login?error");		
	}

}
