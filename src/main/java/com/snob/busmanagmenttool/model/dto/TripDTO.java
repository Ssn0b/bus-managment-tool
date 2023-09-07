package com.snob.busmanagmenttool.model.dto;

import com.snob.busmanagmenttool.model.entity.machinery.Bus;
//import com.snob.busmanagmenttool.model.entity.route.TripStop;
import com.snob.busmanagmenttool.model.entity.route.Stop;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Validated
public class TripDTO {
    private Long id;
    private String tripNumber;
    @NotNull(message  = "Bus id is required")
    @Positive(message = "Bus ID must be a positive integer")
    private Long busId;
    @NotNull(message  = "Departure time is required")
    @FutureOrPresent
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime departureTime;
    @NotNull(message  = "Arrival time is required")
    @FutureOrPresent
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime arrivalTime;
    private Long startStopId;
    private Long finishStopId;
}
