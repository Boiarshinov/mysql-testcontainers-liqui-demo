package dev.boiarshinov.mysqltestcontainersliquidemo.model;

import java.time.Instant;
import java.time.LocalDate;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
public class CatEntity {
	long id;
	@EqualsAndHashCode.Exclude Instant createdAt;
	String name;
	LocalDate bornAt;
	Type type;

	public enum Type {
		PERSIAN, HOMELESS, NOBEL
	}
}
