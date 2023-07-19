package com.snob.busmanagmenttool.model.entity.route;

import com.snob.busmanagmenttool.model.entity.machinery.Bus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "_route")
public class Route {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "route_id")
    private List<Stop> stops;
    private double distance;
    private Duration duration;
    @OneToOne
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

}
