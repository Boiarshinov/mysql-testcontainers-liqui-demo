package dev.boiarshinov.mysqltestcontainersliquidemo.storage;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;

import dev.boiarshinov.mysqltestcontainersliquidemo.model.CatEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

@Component
public class CatStore {

	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert catInsert;

	public CatStore(@Autowired DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.catInsert = new SimpleJdbcInsert( dataSource )
				.usingColumns( "name", "born_at", "type", "created_at" )
				.withSchemaName( "first_schema" )
				.withTableName( "cat" )
				.usingGeneratedKeyColumns( "id" );
		catInsert.withoutTableColumnMetaDataAccess();
	}

	public long saveAndGetId(CatEntity catEntity) {
		return catInsert.executeAndReturnKey( Map.of(
				"name", catEntity.getName(),
				"born_at", catEntity.getBornAt(),
				"type", catEntity.getType().name(),
				"created_at", Timestamp.from( Instant.now() )
		) ).longValue();
	}

	public Optional<CatEntity> getById(long id) {
		try {
			final CatEntity catEntity = jdbcTemplate.queryForObject(
					"select * from first_schema.cat where id = ?",
					(resultSet, rowNum) ->
							CatEntity.builder()
									.id( resultSet.getLong( "id" ) )
									.createdAt( resultSet.getTimestamp( "created_at" ).toInstant() )
									.name( resultSet.getString( "name" ) )
									.bornAt( resultSet.getDate( "born_at" ).toLocalDate() )
									.type( CatEntity.Type.valueOf( resultSet.getString( "type" ) ) )
									.build(),
					id
			);
			return Optional.ofNullable( catEntity );
		} catch (EmptyResultDataAccessException ex) {
			return Optional.empty();
		}
	}
}
