package com.micro.cadastroQueo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "vehicle_tb")
public class VehicleEntity {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false, unique = true)
	    @NotEmpty(message = "Plate cannot be empty")
	    private String plate;

	    @Version
	    private Long version;
		public VehicleEntity() {
			
		}
		

		public VehicleEntity(String plate) {
			this.plate = plate;
		}


		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getPlate() {
			return plate;
		}

		public void setPlate(String plate) {
			this.plate = plate;
		}
		
		

	    
}
