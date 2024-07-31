package com.micro.cadastroQueo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import com.micro.cadastroQueo.entities.VehicleEntity;

import jakarta.persistence.LockModeType;

public interface VehicleRepository extends JpaRepository<VehicleEntity, Long>{
	   Optional<VehicleEntity> findByPlate(String plate);
	   @Query("SELECT v FROM VehicleEntity v WHERE v.plate = :plate")
	    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
	    Optional<VehicleEntity> findByPlateWithOptimisticLock(String plate);
	   
}
