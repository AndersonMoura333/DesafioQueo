package com.micro.associacaoQueo.dto;

import java.time.LocalDateTime;

public record AssociationByPeriodRequest(String plate, LocalDateTime startDateTime, LocalDateTime endDateTime) {

}
