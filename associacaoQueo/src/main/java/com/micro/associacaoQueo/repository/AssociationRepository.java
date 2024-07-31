package com.micro.associacaoQueo.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.micro.associacaoQueo.entity.AssociationEntity;


public interface AssociationRepository extends JpaRepository<AssociationEntity, Long>{
	
	@Query("SELECT a FROM AssociationEntity a " +
		       "WHERE a.vehiclePlate = :vehiclePlate " +
		       "AND a.startDateTime BETWEEN :startDateTimeStart AND :startDateTimeEnd")
		Optional<List<AssociationEntity>> findByVehiclePlateAndStartDateTimeBetween(
		    @Param("vehiclePlate") String vehiclePlate, 
		    @Param("startDateTimeStart") LocalDateTime startDateTimeStart, 
		    @Param("startDateTimeEnd") LocalDateTime startDateTimeEnd);

//	    Optional<AssociationEntity> findByVehiclePlateAndDriverRegistrationAndSatDateTime(String vehiclePlate,String driverRegistration);

	    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END " +
	            "FROM AssociationEntity a " +
	            "WHERE a.vehiclePlate = :vehiclePlate " +
	            "AND a.driverRegistration != :driverRegistration " +
	            "AND (a.endDateTime IS NULL OR a.endDateTime >= :startDateTime)")
	     boolean isVehicleAssociatedToAnotherDriver(
	         @Param("vehiclePlate") String vehiclePlate,
	         @Param("driverRegistration") String driverRegistration,
	         @Param("startDateTime") LocalDateTime startDateTime);

	    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END " +
	            "FROM AssociationEntity a " +
	            "WHERE a.driverRegistration = :driverRegistration " +
	            "AND (a.endDateTime IS NULL OR a.endDateTime >= :startDateTime)")
	     boolean isDriverCurrentlyAssociated(
	         @Param("driverRegistration") String driverRegistration,
	         @Param("startDateTime") LocalDateTime startDateTime);
	    
	    @Query("SELECT a FROM AssociationEntity a " +
	    	       "WHERE a.vehiclePlate = :vehiclePlate " +
	    	       "AND a.driverRegistration = :driverRegistration " +
	    	       "AND a.endDateTime IS NULL"
	    		)
	    	     
	    	Optional<AssociationEntity> findActiveAssociation(
	    	    @Param("vehiclePlate") String vehiclePlate,
	    	    @Param("driverRegistration") String driverRegistration);

	    Optional<AssociationEntity> findFirstByDriverRegistrationOrderByStartDateTimeDesc(String driverRegistration);
	    
	
}
