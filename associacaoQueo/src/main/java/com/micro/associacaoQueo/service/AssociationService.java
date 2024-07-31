package com.micro.associacaoQueo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.micro.associacaoQueo.dto.AssociationByPeriodRequest;
import com.micro.associacaoQueo.dto.AssociationRequest;
import com.micro.associacaoQueo.dto.AssociationResponse;
import com.micro.associacaoQueo.dto.AssociationStatusResponse;
import com.micro.associacaoQueo.dto.DriverEntity;
import com.micro.associacaoQueo.dto.VehicleEntity;
import com.micro.associacaoQueo.entity.AssociationEntity;
import com.micro.associacaoQueo.exceptions.AlreadyAssociatedException;
import com.micro.associacaoQueo.exceptions.EntityNotFoundException;
import com.micro.associacaoQueo.exceptions.InvalidAssociationException;
import com.micro.associacaoQueo.feignClients.CadastrationFeignClient;
import com.micro.associacaoQueo.repository.AssociationRepository;


@Service
public class AssociationService {

    @Autowired
    private AssociationRepository associationRepository;

    @Autowired
    private CadastrationFeignClient cadastrationFeignClient;
    private static final Logger logger = LoggerFactory.getLogger(AssociationService.class);

    @Transactional
    public AssociationResponse login(AssociationRequest associationRequest) {
    	DriverEntity existsDriver = cadastrationFeignClient.existsDriverByRegistration(associationRequest.driverRegistration()).getBody();
    	logger.info("driver id {}", existsDriver.id());
    	if(existsDriver.id() == null) {
    		throw new EntityNotFoundException("Driver Registration");
    	}
    	
    	VehicleEntity existsVehicle = cadastrationFeignClient.existsVehicleByPlate(associationRequest.vehiclePlate()).getBody();
    	if(existsVehicle.id() == null) {
    		throw new EntityNotFoundException("Vehicle Plate");
    	}
        boolean isVehicleAssociatedToAnotherDriver = associationRepository.isVehicleAssociatedToAnotherDriver(
                associationRequest.vehiclePlate(), 
                associationRequest.driverRegistration(),
                associationRequest.dateTime()
        );

        if (isVehicleAssociatedToAnotherDriver) {
        	 throw new AlreadyAssociatedException("Vehicle already associated with another driver during this period");
        }

        boolean isDriverCurrentlyAssociated = associationRepository.isDriverCurrentlyAssociated(
                associationRequest.driverRegistration(), 
                associationRequest.dateTime()
        );

        if (isDriverCurrentlyAssociated) {
        	 throw new AlreadyAssociatedException("Driver already associated in this period");
        }

        AssociationEntity createdAssociation = associationRepository.save(associationRequest.toAssociation());
        return new AssociationResponse(createdAssociation.getId());
    }

    public AssociationResponse logout(AssociationRequest associationRequest) {
        AssociationEntity associationEntity = associationRepository.findActiveAssociation(
                associationRequest.vehiclePlate(), 
                associationRequest.driverRegistration())
                .orElseThrow(() -> new EntityNotFoundException("Active association"));

        if (associationRequest.dateTime().isBefore(associationEntity.getStartDateTime())) {
        	 throw new InvalidAssociationException("Invalid end time");
        }

        associationEntity.setEndDateTime(associationRequest.dateTime());
        associationRepository.save(associationEntity);
        logger.info("Updated association: {}", associationEntity);
        return new AssociationResponse(associationEntity.getId());
    }

    public List<AssociationEntity> getAssociacoesByPlateAndPeriod(AssociationByPeriodRequest request) {
        logger.info("Fetching associations for plate: {}, between {} and {}", 
                     request.plate(), request.startDateTime(), request.endDateTime());

        List<AssociationEntity> associationList = associationRepository
                .findByVehiclePlateAndStartDateTimeBetween(
                        request.plate(), 
                        request.startDateTime(), 
                        request.endDateTime())
                .orElseThrow(() -> new EntityNotFoundException("Associations"));

        associationList.forEach(e -> logger.info("Found entity: {}", e));
        return associationList;
    }
    
    public AssociationStatusResponse getAssociacaoStatusByCondutor(String driverRegistration) {
        AssociationEntity association = associationRepository.findFirstByDriverRegistrationOrderByStartDateTimeDesc(driverRegistration)
                .orElseThrow(() -> new EntityNotFoundException("Association"));

        if (association.getEndDateTime() == null) {
            return new AssociationStatusResponse(association.getId(), association.getVehiclePlate(), association.getStartDateTime(), "Associado");
        } else {
            return new AssociationStatusResponse(null, null, null, "Disponível");
        }
    }
}
