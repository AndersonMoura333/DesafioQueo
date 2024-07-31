package com.micro.cadastroQueo.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.micro.cadastroQueo.dto.VehicleRequest;
import com.micro.cadastroQueo.dto.VehicleResponse;
import com.micro.cadastroQueo.entities.VehicleEntity;
import com.micro.cadastroQueo.exceptions.AlreadyRegisteredException;
import com.micro.cadastroQueo.exceptions.EntityNotFoundException;
import com.micro.cadastroQueo.exceptions.InvalidDataException;
import com.micro.cadastroQueo.repositories.VehicleRepository;

class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleService vehicleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveVehicleWhenPlateExists() {
        VehicleRequest vehicleRequest = new VehicleRequest("ABC123");
        VehicleEntity existingVehicle = new VehicleEntity();
        existingVehicle.setPlate("ABC123");

        when(vehicleRepository.findByPlate(vehicleRequest.plate())).thenReturn(Optional.of(existingVehicle));

        Exception exception = assertThrows(AlreadyRegisteredException.class, () -> {
            vehicleService.saveVehicle(vehicleRequest);
        });

        assertEquals("Vehicle with this plate is already registered", exception.getMessage());
    }

    @Test
    void testSaveVehicleSuccess() {
        VehicleRequest vehicleRequest = new VehicleRequest("ABC123");
        VehicleEntity vehicleEntity = new VehicleEntity();
        vehicleEntity.setPlate("ABC123");
        vehicleEntity.setId(1L);

        when(vehicleRepository.findByPlate(vehicleRequest.plate())).thenReturn(Optional.empty());
        when(vehicleRepository.save(any(VehicleEntity.class))).thenReturn(vehicleEntity);

        VehicleResponse response = vehicleService.saveVehicle(vehicleRequest);

        assertNotNull(response);
        assertEquals(1L, response.id());
    }


    @Test
    void testGetVehicleByIdSuccess() {
        Long id = 1L;
        VehicleEntity vehicleEntity = new VehicleEntity();
        vehicleEntity.setId(id);

        when(vehicleRepository.findById(id)).thenReturn(Optional.of(vehicleEntity));

        VehicleEntity foundVehicle = vehicleService.getVehicleById(id);

        assertNotNull(foundVehicle);
        assertEquals(id, foundVehicle.getId());
    }

    @Test
    void testGetVehicleByIdNotFound() {
        Long id = 1L;

        when(vehicleRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            vehicleService.getVehicleById(id);
        });

        assertEquals("Vehicle not found", exception.getMessage());
    }

    @Test
    void testExistsVehicleByPlate() {
        String plate = "ABC123";
        VehicleEntity vehicleEntity = new VehicleEntity();
        vehicleEntity.setPlate(plate);

        when(vehicleRepository.findByPlateWithOptimisticLock(plate)).thenReturn(Optional.of(vehicleEntity));

        VehicleEntity result = vehicleService.existsVehicleByPlate(plate);

        assertNotNull(result);
        assertEquals(plate, result.getPlate());
    }

    @Test
    void testDeleteVehicleByIdSuccess() {
        Long id = 1L;
        VehicleEntity vehicleEntity = new VehicleEntity();
        vehicleEntity.setId(id);

        when(vehicleRepository.findById(id)).thenReturn(Optional.of(vehicleEntity));
        doNothing().when(vehicleRepository).delete(vehicleEntity);

        vehicleService.deleteVehicleById(id);

        verify(vehicleRepository, times(1)).delete(vehicleEntity);
    }

    @Test
    void testDeleteVehicleByIdNotFound() {
        Long id = 1L;

        when(vehicleRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            vehicleService.deleteVehicleById(id);
        });

        assertEquals("Vehicle not found", exception.getMessage());
    }

    @Test
    void testUpdateVehicleByIdSuccess() throws NoSuchFieldException, IllegalAccessException {
        Long id = 1L;
        Map<String, Object> updates = new HashMap<>();
        updates.put("plate", "dsdsds");

        VehicleEntity vehicleEntity = new VehicleEntity();
        vehicleEntity.setId(id);

        when(vehicleRepository.findById(id)).thenReturn(Optional.of(vehicleEntity));
        when(vehicleRepository.save(any(VehicleEntity.class))).thenReturn(vehicleEntity);

        VehicleEntity updatedVehicle = vehicleService.updateVehicleById(id, updates);

        assertNotNull(updatedVehicle);
        assertEquals(id, updatedVehicle.getId());
    }

    @Test
    void testUpdateVehicleByIdInvalidField() {
        Long id = 1L;
        Map<String, Object> updates = new HashMap<>();
        updates.put("invalidField", "value");

        VehicleEntity vehicleEntity = new VehicleEntity();
        vehicleEntity.setId(id);

        when(vehicleRepository.findById(id)).thenReturn(Optional.of(vehicleEntity));

        Exception exception = assertThrows(InvalidDataException.class, () -> {
            vehicleService.updateVehicleById(id, updates);
        });

        assertEquals("Invalid data for Vehicle: Invalid field: invalidField", exception.getMessage());
    }
}
