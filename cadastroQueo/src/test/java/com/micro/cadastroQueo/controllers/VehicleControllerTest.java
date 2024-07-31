package com.micro.cadastroQueo.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.cadastroQueo.dto.VehicleRequest;
import com.micro.cadastroQueo.dto.VehicleResponse;
import com.micro.cadastroQueo.entities.VehicleEntity;
import com.micro.cadastroQueo.services.VehicleService;

@WebMvcTest(VehicleController.class)
class VehicleControllerTest {

    @MockBean
    private VehicleService vehicleService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.objectMapper = new ObjectMapper();
    }

    @Test
    void testCreateVehicle() throws Exception {
        VehicleRequest vehicleRequest = new VehicleRequest("ABC123");
        VehicleResponse vehicleResponse = new VehicleResponse(1L);

        when(vehicleService.saveVehicle(any(VehicleRequest.class))).thenReturn(vehicleResponse);

        mockMvc.perform(post("/vehicle")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vehicleRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(vehicleService, times(1)).saveVehicle(any(VehicleRequest.class));
    }

    @Test
    void testGetVehicle() throws Exception {
        Long id = 1L;
        VehicleEntity vehicleEntity = new VehicleEntity();
        vehicleEntity.setId(id);
        vehicleEntity.setPlate("ABC123");
    

        when(vehicleService.getVehicleById(id)).thenReturn(vehicleEntity);

        mockMvc.perform(get("/vehicle/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.plate").value("ABC123"));
          

        verify(vehicleService, times(1)).getVehicleById(id);
    }

    @Test
    void testGetVehicleByPlate() throws Exception {
        String plate = "ABC123";
        VehicleEntity vehicleEntity = new VehicleEntity();
        vehicleEntity.setId(1L);
        vehicleEntity.setPlate(plate);
      
        when(vehicleService.existsVehicleByPlate(plate)).thenReturn(vehicleEntity);

        mockMvc.perform(get("/vehicle/find-plate/{plate}", plate))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.plate").value(plate));
        verify(vehicleService, times(1)).existsVehicleByPlate(plate);
    }

    @Test
    void testDeleteVehicle() throws Exception {
        Long id = 1L;

        doNothing().when(vehicleService).deleteVehicleById(id);

        mockMvc.perform(delete("/vehicle/{id}", id))
                .andExpect(status().isNoContent());

        verify(vehicleService, times(1)).deleteVehicleById(id);
    }

    @Test
    void testUpdateVehicle() throws Exception {
        Long id = 1L;
        Map<String, Object> updates = new HashMap<>();
        updates.put("name", "Updated Vehicle");

        VehicleEntity updatedVehicle = new VehicleEntity();
        updatedVehicle.setId(id);
        updatedVehicle.setPlate("ABC123");
        

        when(vehicleService.updateVehicleById(eq(id), any(Map.class))).thenReturn(updatedVehicle);

        mockMvc.perform(patch("/vehicle/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updates)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));

        verify(vehicleService, times(1)).updateVehicleById(eq(id), any(Map.class));
    }
}
