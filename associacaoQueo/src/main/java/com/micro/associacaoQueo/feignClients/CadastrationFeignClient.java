package com.micro.associacaoQueo.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.micro.associacaoQueo.dto.DriverEntity;
import com.micro.associacaoQueo.dto.VehicleEntity;



@Component
@FeignClient(name = "cadastroQueo", url = "http://localhost:8080")
public interface CadastrationFeignClient {

	@GetMapping("driver/registration/{registration}")
	public ResponseEntity<DriverEntity> existsDriverByRegistration(@PathVariable String registration);
	

	@GetMapping("vehicle/find-plate/{plate}")
	public ResponseEntity<VehicleEntity> existsVehicleByPlate(@PathVariable String plate);
}