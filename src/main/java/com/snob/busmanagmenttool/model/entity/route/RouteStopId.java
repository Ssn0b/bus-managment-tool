package com.snob.busmanagmenttool.model.entity.route;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class RouteStopId implements Serializable {
    @Column(name = "route_id")
    private Long routeId;
    @Column(name = "stop_id")
    private Long stopId;
}
