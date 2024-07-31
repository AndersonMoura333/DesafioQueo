package com.micro.associacaoQueo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "association_tb")
public class AssociationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	@NotEmpty(message = "Vehicle plate cannot be empty")
	private String vehiclePlate;
	 
	@Column(nullable = false)
	@NotEmpty(message = "Driver registration cannot be empty")
	private String driverRegistration;

	@Column(nullable = false)
	private LocalDateTime startDateTime;
	
	
	private LocalDateTime endDateTime;

	public AssociationEntity() {
		
	}
	
	

	public AssociationEntity(String vehiclePlate, String driverRegistration, LocalDateTime startDateTime) {

		this.vehiclePlate = vehiclePlate;
		this.driverRegistration = driverRegistration;
		this.startDateTime = startDateTime;
	
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVehiclePlate() {
		return vehiclePlate;
	}

	public void setVehiclePlate(String vehiclePlate) {
		this.vehiclePlate = vehiclePlate;
	}

	public String getDriverRegistration() {
		return driverRegistration;
	}

	public void setDriverRegistration(String driverRegistration) {
		this.driverRegistration = driverRegistration;
	}

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(LocalDateTime endDateTime) {
		this.endDateTime = endDateTime;
	}

	
	
	
	
	
}
