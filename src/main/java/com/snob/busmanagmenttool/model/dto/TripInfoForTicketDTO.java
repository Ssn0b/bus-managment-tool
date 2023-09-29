package com.snob.busmanagmenttool.model.dto;

import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Validated
public class TripInfoForTicketDTO {
    private String tripNumber;
    private String carNumber;
    private String driverFullName;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String startCity;
    private String endCity;
    private Double ticketPrice;
}
