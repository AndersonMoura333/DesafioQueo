package com.micro.cadastroQueo.services;

import java.lang.reflect.Field;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import com.micro.cadastroQueo.dto.DriverRequest;
import com.micro.cadastroQueo.dto.DriverResponse;
import com.micro.cadastroQueo.entities.DriverEntity;
import com.micro.cadastroQueo.exceptions.AlreadyRegisteredException;
import com.micro.cadastroQueo.exceptions.EntityNotFoundException;
import com.micro.cadastroQueo.exceptions.InvalidDataException;
import com.micro.cadastroQueo.exceptions.ServiceException;
import com.micro.cadastroQueo.repositories.DriverRepository;



@Service
public class DriverService {

	@Autowired
	private DriverRepository driverRepository;
	
	@Transactional
	public DriverResponse saveDriver(DriverRequest driverRequest) {
		boolean driverExists = driverRepository.findByRegistration(driverRequest.registration()).isPresent();
		//get or throw
		if(driverExists) {
			 throw new AlreadyRegisteredException("Driver", "registration");
		}
		  try {
	            DriverEntity createdDriver = driverRepository.save(driverRequest.toDriver());
	            return new DriverResponse(createdDriver.getId());
	        } catch (Exception e) {
	        	 throw new ServiceException("Driver");
	        }
	}
	
	@Transactional
	public DriverEntity getDriverById(Long id) {
		return driverRepository.findById(id)
        		.orElseThrow(() -> new EntityNotFoundException("Driver"));
       
    }
	
	
	@Transactional(isolation =  Isolation.SERIALIZABLE)
	public DriverEntity findByRegistrationWithPessimisticLock(String registration) {
        DriverEntity driverEntity = driverRepository.findByRegistrationWithPessimisticLock(registration).get();
        return driverRepository.save(driverEntity);
    }
	
	@Transactional
	public void deleteDriverById(Long id) {
		
        DriverEntity driverFound = driverRepository.findById(id)
        		.orElseThrow(() -> new EntityNotFoundException("Driver"));
        driverRepository.delete(driverFound);
    }
	
	@Transactional
	public DriverEntity updateDriverById(Long id, Map<String, Object> updates) {
        DriverEntity driver = driverRepository.findById(id)
        		.orElseThrow(() -> new EntityNotFoundException("Driver"));
        DriverEntity driverFinal = driver;
        try {
            updates.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(DriverEntity.class, key);
                if (field != null) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, driverFinal, value);
                } else {
                	 throw new InvalidDataException("Driver", "Invalid field: " + key);
                }
            });

 
            return driverRepository.save(driverFinal);
        } catch (Exception e) {
            throw new ServiceException("Driver");
        }
        
    }
}
