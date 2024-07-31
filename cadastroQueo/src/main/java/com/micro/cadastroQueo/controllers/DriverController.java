package com.micro.cadastroQueo.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.micro.cadastroQueo.dto.DriverRequest;
import com.micro.cadastroQueo.dto.DriverResponse;
import com.micro.cadastroQueo.entities.DriverEntity;
import com.micro.cadastroQueo.services.DriverService;

@RestController
@RequestMapping("/driver")
public class DriverController {
	
	@Autowired
	private DriverService driverService;

	@PostMapping
	public ResponseEntity<DriverResponse> postDriver(@RequestBody DriverRequest driverRequest){
		DriverResponse driverResponse = driverService.saveDriver(driverRequest);
		return ResponseEntity.ok(driverResponse);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DriverEntity> getDriver(@PathVariable Long id){
		DriverEntity driverEntity = driverService.getDriverById(id);
		return ResponseEntity.ok(driverEntity);
	}
	

	@DeleteMapping("/{id}")
	public ResponseEntity<DriverEntity> deleteDriver(@PathVariable Long id){
		driverService.deleteDriverById(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("registration/{registration}")
	public ResponseEntity<DriverEntity> getDriverByRegistration(@PathVariable String registration){
		return ResponseEntity.ok(driverService.findByRegistrationWithPessimisticLock(registration));
	}
	
	@PatchMapping("/{id}")	
	public ResponseEntity<DriverEntity> updateDriver(@PathVariable Long id, @RequestBody Map<String, Object> updates){
		DriverEntity driverEntity = driverService.updateDriverById(id, updates);
		return ResponseEntity.ok(driverEntity);
	}

}
