package com.micro.cadastroQueo.dto;

import com.micro.cadastroQueo.entities.VehicleEntity;

public record VehicleRequest(String plate) {

	public VehicleEntity toVehicle() {
		return new VehicleEntity(plate);
	}
}
