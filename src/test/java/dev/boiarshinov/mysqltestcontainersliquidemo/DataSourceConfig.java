package dev.boiarshinov.mysqltestcontainersliquidemo;

import javax.sql.DataSource;

import com.mysql.cj.jdbc.Driver;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;

@Configuration
@ActiveProfiles("test")
public class DataSourceConfig {

	private static final MySQLContainer mysqlContainer = new MySQLContainer("mysql:8.0.23")
			//for connecting to testcontainer with db tools while debugging
			.withUsername( "user" )
			.withPassword( "pass" );

	static {
		mysqlContainer.start();
	}

	@Bean
	public DataSource dataSource() {
		return DataSourceBuilder.create()
				.url( mysqlContainer.getJdbcUrl() )
				.username( mysqlContainer.getUsername() )
				.password( mysqlContainer.getPassword() )
				.driverClassName( Driver.class.getName() )
				.build();
	}
}
