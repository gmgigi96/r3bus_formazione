package org.bitbucket.r3bus;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@ImportResource({ "classpath:webSecurityConfig.xml" })
@EnableWebSecurity
public class SecurityConfig {
	public SecurityConfig() {
		super();
	}
}
