package dev.boiarshinov.mysqltestcontainersliquidemo;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.boiarshinov.mysqltestcontainersliquidemo.model.CatEntity;
import dev.boiarshinov.mysqltestcontainersliquidemo.storage.CatStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@ActiveProfiles("test")
public class CatStoreTest {

	@Autowired private CatStore store;

	@Sql(scripts = "/db/clean_up.sql")
	@BeforeEach
	void tearDown() { }

	@Test
	void saveAndGetId() {
		final CatEntity fluffy = CatEntity.builder()
				.name( "Fluffy" )
				.bornAt( LocalDate.of( 2016, 5, 15 ) )
				.type( CatEntity.Type.HOMELESS ).build();

		final long id = store.saveAndGetId( fluffy );

		Assertions.assertEquals( 1L, id );
	}
}
