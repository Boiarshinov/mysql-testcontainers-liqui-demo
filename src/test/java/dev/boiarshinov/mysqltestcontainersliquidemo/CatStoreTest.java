package dev.boiarshinov.mysqltestcontainersliquidemo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import dev.boiarshinov.mysqltestcontainersliquidemo.model.CatEntity;
import dev.boiarshinov.mysqltestcontainersliquidemo.storage.CatStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;

@Sql(scripts = "/db/clean_up.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest
@ActiveProfiles("test")
public class CatStoreTest {

	@Autowired private CatStore store;

	@Test
	void saveAndGetId() {
		final CatEntity fluffy = CatEntity.builder()
				.name( "Fluffy" )
				.bornAt( LocalDate.of( 2016, 5, 15 ) )
				.type( CatEntity.Type.HOMELESS ).build();

		final long id = store.saveAndGetId( fluffy );

		assertEquals( 1L, id );
	}

	@Test
	@Sql(scripts = "/db/insert/cat_get_by_id.sql")
	@SqlMergeMode( SqlMergeMode.MergeMode.MERGE )
	void getById() {
		final CatEntity expectedResult = CatEntity.builder()
				.id( 1 )
				.name( "Fluffy" )
				.bornAt( LocalDate.of( 2016, 5, 15 ) )
				.type( CatEntity.Type.HOMELESS )
				.build();

		final Optional<CatEntity> catOpt = store.getById( 1L );

		assertTrue( catOpt.isPresent() );
		assertEquals( expectedResult, catOpt.get() );
	}
}
