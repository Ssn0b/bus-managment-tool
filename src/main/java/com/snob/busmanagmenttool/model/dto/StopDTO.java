package com.snob.busmanagmenttool.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class StopDTO {
    private Long id;
    private CityDTO city;
    private String street;
}
