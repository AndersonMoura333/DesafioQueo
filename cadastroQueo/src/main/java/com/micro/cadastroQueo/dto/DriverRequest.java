package com.micro.cadastroQueo.dto;

import com.micro.cadastroQueo.entities.DriverEntity;

public record DriverRequest (String name, String registration) {

	public DriverEntity toDriver() {
		return new DriverEntity(name,registration);
	}
}
