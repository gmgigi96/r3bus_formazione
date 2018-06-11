package org.bitbucket.r3bus;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final String userQuery = "SELECT username, pw, TRUE FROM utenti WHERE username = ?";
	private final String authQuery = "SELECT username, auth FROM utenti WHERE username = ?";

	@Qualifier("dataSource")
	@Autowired
	private DataSource dataSource;

//	@Bean
//	public BCryptPasswordEncoder bCryptPasswordEncoder() {
//		return new BCryptPasswordEncoder();
//	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
			.passwordEncoder(getPasswordEncoder())
			.usersByUsernameQuery(userQuery)
			.authoritiesByUsernameQuery(authQuery);
	}

	// @Override
	// public void configure(WebSecurity web) throws Exception {
	// web.ignoring().antMatchers("/favicon.ico", "/login*", "/error", "/js/**",
	// "/css/**", "/images/**");
	// }

	private PasswordEncoder getPasswordEncoder() {
		return new PasswordEncoder() {
			@Override
			public String encode(CharSequence charSequence) {
				return charSequence.toString();
			}

			@Override
			public boolean matches(CharSequence charSequence, String s) {
				return true;
			}
		};
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
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
