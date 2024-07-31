package com.micro.cadastroQueo.services;

import java.lang.reflect.Field;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import com.micro.cadastroQueo.dto.VehicleRequest;
import com.micro.cadastroQueo.dto.VehicleResponse;
import com.micro.cadastroQueo.entities.VehicleEntity;
import com.micro.cadastroQueo.exceptions.AlreadyRegisteredException;
import com.micro.cadastroQueo.exceptions.EntityNotFoundException;
import com.micro.cadastroQueo.exceptions.InvalidDataException;
import com.micro.cadastroQueo.repositories.VehicleRepository;





@Service
public class VehicleService {

	@Autowired
	private VehicleRepository vehicleRepository;
	  @Transactional
	public VehicleResponse saveVehicle(VehicleRequest vehicleRequest) {
		boolean plateExists = vehicleRepository.findByPlate(vehicleRequest.plate()).isEmpty();
		if(!plateExists) {
			throw new AlreadyRegisteredException("Vehicle", "plate");
		}
		VehicleEntity createdVehicle = vehicleRepository.save(vehicleRequest.toVehicle());
		return new VehicleResponse(createdVehicle.getId());
	}
	
	public VehicleEntity getVehicleById(Long id) {
		VehicleEntity vehicleFound = vehicleRepository.findById(id)
        		.orElseThrow(() -> new EntityNotFoundException("Vehicle"));
        return vehicleFound;
    }
	
	  @Transactional(isolation = Isolation.SERIALIZABLE)
	public VehicleEntity existsVehicleByPlate(String plate) {
		VehicleEntity existsvehicle = vehicleRepository.findByPlateWithOptimisticLock(plate).get();
        return existsvehicle;
    }
	@Transactional
	public void deleteVehicleById(Long id) {
		VehicleEntity vehicleFound = vehicleRepository.findById(id)
        		.orElseThrow(() -> new EntityNotFoundException("Vehicle"));
		vehicleRepository.delete(vehicleFound);
    }
	
	@Transactional
	public VehicleEntity updateVehicleById(Long id, Map<String, Object> updates) {
		VehicleEntity vehicle = vehicleRepository.findById(id)
        		.orElseThrow(() -> new EntityNotFoundException("Vehicle"));
		VehicleEntity vehicleFinal = vehicle;
        updates.forEach((key, value) -> {
        	Field field = ReflectionUtils.findField(VehicleEntity.class, key);
        	 if (field != null) {
                 field.setAccessible(true);
                 ReflectionUtils.setField(field, vehicleFinal, value);
             } else {
                 throw new InvalidDataException("Vehicle", "Invalid field: " + key);
             }
        });
        
        return vehicleRepository.save(vehicleFinal);
        
    }
}
