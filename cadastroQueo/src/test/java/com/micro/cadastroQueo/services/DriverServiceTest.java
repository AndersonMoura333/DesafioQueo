package com.micro.cadastroQueo.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.micro.cadastroQueo.dto.DriverRequest;
import com.micro.cadastroQueo.dto.DriverResponse;
import com.micro.cadastroQueo.entities.DriverEntity;
import com.micro.cadastroQueo.exceptions.AlreadyRegisteredException;
import com.micro.cadastroQueo.exceptions.EntityNotFoundException;
import com.micro.cadastroQueo.repositories.DriverRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;


class DriverServiceTest {

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private DriverService driverService;

    @Mock
    private EntityManager entityManager;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveDriver_DriverExists() {
        DriverRequest driverRequest = new DriverRequest("teste","ABC123");
        DriverEntity driverEntity = new DriverEntity("teste","ABC123");
        when(driverRepository.findByRegistration(driverRequest.registration())).thenReturn(Optional.of(driverEntity));

        assertThrows(AlreadyRegisteredException.class, () -> driverService.saveDriver(driverRequest));

        verify(driverRepository, times(1)).findByRegistration(driverRequest.registration());
    }

    @Test
    void testSaveDriver_Success() {
        DriverRequest driverRequest = new DriverRequest("teste","ABC123");
        DriverEntity driverEntity = new DriverEntity();
        driverEntity.setId(1L);

        when(driverRepository.findByRegistration(driverRequest.registration())).thenReturn(Optional.empty());
        when(driverRepository.save(any(DriverEntity.class))).thenReturn(driverEntity);

        DriverResponse response = driverService.saveDriver(driverRequest);

        assertNotNull(response);
        assertEquals(1L, response.id());

        verify(driverRepository, times(1)).findByRegistration(driverRequest.registration());
        verify(driverRepository, times(1)).save(any(DriverEntity.class));
    }

    @Test
    void testGetDriverById_NotFound() {
        Long id = 1L;

        when(driverRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> driverService.getDriverById(id));

        verify(driverRepository, times(1)).findById(id);
    }

    @Test
    void testGetDriverById_Success() {
        Long id = 1L;
        DriverEntity driverEntity = new DriverEntity();
        driverEntity.setId(id);

        when(driverRepository.findById(id)).thenReturn(Optional.of(driverEntity));

        DriverEntity result = driverService.getDriverById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());

        verify(driverRepository, times(1)).findById(id);
    }

    @Test
    void testDeleteDriverById_NotFound() {
        Long id = 1L;

        when(driverRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> driverService.deleteDriverById(id));

        verify(driverRepository, times(1)).findById(id);
    }

    @Test
    void testDeleteDriverById_Success() {
        Long id = 1L;
        DriverEntity driverEntity = new DriverEntity();
        driverEntity.setId(id);

        when(driverRepository.findById(id)).thenReturn(Optional.of(driverEntity));

        driverService.deleteDriverById(id);

        verify(driverRepository, times(1)).findById(id);
        verify(driverRepository, times(1)).delete(driverEntity);
    }

    @Test
    void testUpdateDriverById_NotFound() {
        Long id = 1L;
        Map<String, Object> updates = Map.of("registration", "XYZ789");

        when(driverRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> driverService.updateDriverById(id, updates));

        verify(driverRepository, times(1)).findById(id);
    }


    @Test
    void testUpdateDriverById_Success() {
        Long id = 1L;
        Map<String, Object> updates = Map.of("registration", "XYZ789");
        DriverEntity driverEntity = new DriverEntity();
        driverEntity.setId(id);

        when(driverRepository.findById(id)).thenReturn(Optional.of(driverEntity));
        when(driverRepository.save(any(DriverEntity.class))).thenReturn(driverEntity);

        DriverEntity result = driverService.updateDriverById(id, updates);

        assertNotNull(result);
        assertEquals(id, result.getId());

        verify(driverRepository, times(1)).findById(id);
        verify(driverRepository, times(1)).save(driverEntity);
    }
    
    @Test
    void testOptimisticLock() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(1);

        DriverEntity driverEntity = new DriverEntity();
        driverEntity.setId(1L);
        driverEntity.setRegistration("ABC123");
        driverEntity.setVersion(1L); 

        when(driverRepository.findByRegistrationWithPessimisticLock(driverEntity.getRegistration())).thenReturn(Optional.of(driverEntity));
        when(driverRepository.save(any(DriverEntity.class))).thenAnswer(invocation -> {
            DriverEntity entity = invocation.getArgument(0);
            if (entity.getVersion() <= driverEntity.getVersion()) {
                throw new OptimisticLockException("Optimistic lock exception");
            }
           
            entity.setVersion(entity.getVersion() + 1);
            return entity;
        });

    
        executor.submit(() -> {
            try {
                latch.await();
                driverService.findByRegistrationWithPessimisticLock(driverEntity.getRegistration());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

      
        executor.submit(() -> {
            try {
                latch.await();
                driverService.findByRegistrationWithPessimisticLock(driverEntity.getRegistration());
            } catch (OptimisticLockException e) {
                System.out.println("Lock otimista obtido, recurso bloqueado.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

      
        latch.countDown();

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        verify(driverRepository, times(2)).save(any(DriverEntity.class));
    }


}
