package com.snob.busmanagmenttool.model.entity.route;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalIdCache;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity(name = "Stop")
@Table(name = "_stop")
@NaturalIdCache
@org.hibernate.annotations.Cache(
        usage = CacheConcurrencyStrategy.READ_WRITE
)
public class Stop {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    @ToString.Exclude
    private City city;
    private String street;
    @OneToMany(mappedBy = "stop", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<RouteStop> routeStops = new ArrayList<>();

}
