package com.micro.associacaoQueo.dto;

import java.time.LocalDateTime;

public record AssociationStatusResponse(Long id, String vehiclePlate, LocalDateTime startDateTime, String status) {

}
