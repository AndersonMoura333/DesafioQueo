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
@Table(name = "driver_tb")
public class DriverEntity {

	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false)
	    @NotEmpty(message = "Name cannot be empty")
	    private String name;

	    @Column(nullable = false, unique = true)
	    @NotEmpty(message = "Registration cannot be empty")
	    private String registration;

	    @Version
	    private Long version;
	    
		public DriverEntity() {
			
		}
		

		public DriverEntity(String name, String registration) {
			this.name = name;
			this.registration = registration;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getRegistration() {
			return registration;
		}

		public void setRegistration(String registration) {
			this.registration = registration;
		}


		public Long getVersion() {
			return version;
		}


		public void setVersion(Long version) {
			this.version = version;
		}
	    
		
		
	    
}
