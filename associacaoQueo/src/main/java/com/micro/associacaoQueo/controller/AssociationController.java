package com.micro.associacaoQueo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.micro.associacaoQueo.dto.AssociationByPeriodRequest;
import com.micro.associacaoQueo.dto.AssociationRequest;
import com.micro.associacaoQueo.dto.AssociationResponse;
import com.micro.associacaoQueo.dto.AssociationStatusResponse;
import com.micro.associacaoQueo.entity.AssociationEntity;
import com.micro.associacaoQueo.service.AssociationService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/association")
public class AssociationController {

    @Autowired
    private AssociationService associationService;

    @PostMapping("/login")
    public ResponseEntity<AssociationResponse> login(@RequestBody  @Valid AssociationRequest associationRequest) {
        AssociationResponse associationResponse = associationService.login(associationRequest);
        return new ResponseEntity<>(associationResponse, HttpStatus.CREATED);
    }

    @PatchMapping("/logout")
    public ResponseEntity<AssociationResponse> logout(@RequestBody @Valid AssociationRequest associationRequest) {
        AssociationResponse associationResponse = associationService.logout(associationRequest);
        return new ResponseEntity<>(associationResponse, HttpStatus.ACCEPTED);
    }

    @GetMapping("/findbyperiod")
    public ResponseEntity<List<AssociationEntity>> getAssociacoesPorPlaca(
           @RequestBody @Valid AssociationByPeriodRequest associationByPeriodRequest
    		) {
        List<AssociationEntity> associations = associationService.getAssociacoesByPlateAndPeriod(associationByPeriodRequest);
        return ResponseEntity.ok(associations);
    }
    
    @GetMapping("/status/{driverRegistration}")
    public ResponseEntity<AssociationStatusResponse> getStatusPorCondutor(@PathVariable String driverRegistration) {
    	AssociationStatusResponse statusResponse = associationService.getAssociacaoStatusByCondutor(driverRegistration);
        return ResponseEntity.ok(statusResponse);
    }
}
