package com.snob.busmanagmenttool.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CityDTO {
    private String name;
    private double latitude;
    private double longitude;
}
