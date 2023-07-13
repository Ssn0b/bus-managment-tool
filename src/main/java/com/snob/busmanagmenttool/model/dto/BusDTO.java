package com.snob.busmanagmenttool.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BusDTO{
    private String brand;
    private int seats;
    private String carNumber;
    private Long driverId;
    private boolean active;
}
