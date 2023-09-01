package com.snob.busmanagmenttool.model.entity.route;

import com.snob.busmanagmenttool.model.entity.machinery.Bus;
import com.snob.busmanagmenttool.model.entity.route.ticket.Ticket;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity(name = "Trip")
@Table(name = "_trip")
public class Trip {
    @Id
    @GeneratedValue
    private Long id;
    private String tripNumber;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TripStop> tripStops = new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;
}
