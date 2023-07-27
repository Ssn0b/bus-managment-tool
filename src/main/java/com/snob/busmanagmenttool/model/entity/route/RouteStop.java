package com.snob.busmanagmenttool.model.entity.route;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity(name = "RouteStop")
@Table(name = "route_stop")
public class RouteStop {
    @EmbeddedId
    private RouteStopId id;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("routeId")
    private Route route;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("stopId")
    private Stop stop;
}
