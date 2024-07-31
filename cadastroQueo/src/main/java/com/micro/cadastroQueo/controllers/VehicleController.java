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

import com.micro.cadastroQueo.dto.VehicleRequest;
import com.micro.cadastroQueo.dto.VehicleResponse;
import com.micro.cadastroQueo.entities.VehicleEntity;
import com.micro.cadastroQueo.services.VehicleService;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

	@Autowired
	private VehicleService vehicleService;
	@PostMapping
	public ResponseEntity<VehicleResponse> posrVehicle(@RequestBody VehicleRequest vehicleRequest){
		VehicleResponse vehicleResponse = vehicleService.saveVehicle(vehicleRequest);
		return ResponseEntity.ok(vehicleResponse);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<VehicleEntity> getVehicle(@PathVariable Long id){
		VehicleEntity vehicleEntity = vehicleService.getVehicleById(id);
		return ResponseEntity.ok(vehicleEntity);
	}
	
	@GetMapping("find-plate/{plate}")
	public ResponseEntity<VehicleEntity> getVehicleByPlate(@PathVariable String plate){
		VehicleEntity existsVehicle = vehicleService.existsVehicleByPlate(plate);
		return ResponseEntity.ok(existsVehicle);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<VehicleEntity> deleteVehicle(@PathVariable Long id){
		vehicleService.deleteVehicleById(id);
		return ResponseEntity.noContent().build();
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<VehicleEntity> updateVehicle(@PathVariable Long id, @RequestBody Map<String, Object> updates){
		VehicleEntity vehicleEntity = vehicleService.updateVehicleById(id, updates);
		return ResponseEntity.ok(vehicleEntity);
	}
}
