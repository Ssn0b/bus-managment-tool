package com.snob.busmanagmenttool.model.entity.route;

import com.snob.busmanagmenttool.model.entity.machinery.Bus;
import jakarta.persistence.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity(name = "Route")
@Table(name = "_route")
public class Route {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int routeNumber;
    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RouteStop> routeStops = new ArrayList<>();
    private double distance;
    private Duration duration;
    @OneToOne
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;
}
