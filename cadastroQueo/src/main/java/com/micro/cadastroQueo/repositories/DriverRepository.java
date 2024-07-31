package com.micro.cadastroQueo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import com.micro.cadastroQueo.entities.DriverEntity;
import jakarta.persistence.LockModeType;


public interface DriverRepository extends JpaRepository<DriverEntity, Long> {
  
	 Optional<DriverEntity> findByRegistration(String registration);
    
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Query("SELECT d FROM DriverEntity d WHERE d.registration = :registration")
    Optional<DriverEntity> findByRegistrationWithOptimisticLock(String registration);
}
