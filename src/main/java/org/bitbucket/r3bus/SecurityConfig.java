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

	private final String userQuery = "SELECT username, pw, TRUE FROM utenti WHERE username = ?";
	private final String authQuery = "SELECT username, auth FROM utenti WHERE username = ?";

	@Qualifier("dataSource")
	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
			.passwordEncoder(getPasswordEncoder())
			.usersByUsernameQuery(userQuery)
			.authoritiesByUsernameQuery(authQuery);
	}
	
	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
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
			.defaultSuccessUrl("/loginSuccess").permitAll()
			.failureUrl("/login?error");
	}

}
