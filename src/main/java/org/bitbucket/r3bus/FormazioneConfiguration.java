package org.bitbucket.r3bus;

import java.net.URI;
import java.net.URISyntaxException;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FormazioneConfiguration {

	@ConfigurationProperties(prefix = "datasource.postgres")
	@Bean
	public DataSource dataSource() throws URISyntaxException {
		DataSourceBuilder bds = DataSourceBuilder.create();

		if (System.getenv("DATABASE_URL") != null) {		
			URI dbUri = new URI(System.getenv("DATABASE_URL"));

			String username = dbUri.getUserInfo().split(":")[0];
			String password = dbUri.getUserInfo().split(":")[1];
			String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";

			bds.url(dbUrl);
			bds.username(username);
			bds.password(password);

		} else {
			// default credentials (local build)
			bds.url("jdbc:postgresql://localhost/formazione");
			bds.username("formazione");
			bds.password("formazione");
		}

		return bds.build();
	}
}
