package com.micro.associacaoQueo.dto;

import java.time.LocalDateTime;

import com.micro.associacaoQueo.entity.AssociationEntity;

public record AssociationRequest(String vehiclePlate, 
		String driverRegistration, 
		LocalDateTime dateTime) {

	public AssociationEntity toAssociation() {
		return new AssociationEntity(vehiclePlate, driverRegistration,dateTime);
	}
}
