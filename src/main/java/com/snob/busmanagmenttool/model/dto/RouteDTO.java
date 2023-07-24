package com.snob.busmanagmenttool.model.dto;

import java.time.Duration;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RouteDTO {
    private Long id;
    private String name;
    private List<Long> stopIds;
    private double distance;
    private Duration duration;
    private Long busId;
}
