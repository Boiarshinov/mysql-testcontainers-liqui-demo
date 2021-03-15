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

	@Bean
	public MySQLContainer mySQLContainer() {
		final MySQLContainer mySQLContainer = new MySQLContainer<>( "mysql:8.0.23" )
				//Connect like root user to have access to run init scripts
				.withUsername( "root" )
				.withPassword( "" )
//				.withEnv("MYSQL_ONETIME_PASSWORD", "pass")
				.withInitScript("db/create_schemas.sql");
		mySQLContainer.start();
		return mySQLContainer;
	}

	@Bean
	public DataSource dataSource(final MySQLContainer mysqlContainer) {
		return DataSourceBuilder.create()
				.url( mysqlContainer.getJdbcUrl() )
				.username( mysqlContainer.getUsername() )
				.password( mysqlContainer.getPassword() )
				.driverClassName( Driver.class.getName() )
				.build();
	}
}
