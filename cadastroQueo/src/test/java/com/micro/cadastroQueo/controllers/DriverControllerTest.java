package com.micro.cadastroQueo.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.cadastroQueo.dto.DriverRequest;
import com.micro.cadastroQueo.dto.DriverResponse;
import com.micro.cadastroQueo.entities.DriverEntity;
import com.micro.cadastroQueo.services.DriverService;

@WebMvcTest(DriverController.class)
class DriverControllerTest {

    @MockBean
    private DriverService driverService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.objectMapper = new ObjectMapper();
    }

    @Test
    void testPostDriver() throws Exception {
        DriverRequest driverRequest = new DriverRequest("New Driver", "ABC123");
        DriverResponse driverResponse = new DriverResponse(1L);

        when(driverService.saveDriver(any(DriverRequest.class))).thenReturn(driverResponse);

        mockMvc.perform(post("/driver")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(driverRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(driverService, times(1)).saveDriver(any(DriverRequest.class));
    }

    @Test
    void testGetDriver() throws Exception {
        Long id = 1L;
        DriverEntity driverEntity = new DriverEntity();
        driverEntity.setId(id);

        when(driverService.getDriverById(id)).thenReturn(driverEntity);

        mockMvc.perform(get("/driver/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));

        verify(driverService, times(1)).getDriverById(id);
    }
    
    @Test
    void testGetDriverByRegistration() throws Exception {
        Long id = 1L;
        String registration = "ACB123";
        DriverEntity driverEntity = new DriverEntity();
        driverEntity.setId(id);
        driverEntity.setRegistration(registration);

        when(driverService.findByRegistrationWithPessimisticLock(registration)).thenReturn(driverEntity);

        mockMvc.perform(get("/driver/registration/{registration}", registration))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.registration").value(registration));

        verify(driverService, times(1)).findByRegistrationWithPessimisticLock(registration);
    }

    // Adicione mais testes conforme necessário para cobrir outros endpoints do controlador
}
