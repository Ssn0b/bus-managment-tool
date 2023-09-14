package com.snob.busmanagmenttool.model.dto;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RouteDTO {
    private String name;
    private int routeNumber;
    private List<Long> stopIds;
    private double distance;
    private String duration;
    private UUID busId;
}
