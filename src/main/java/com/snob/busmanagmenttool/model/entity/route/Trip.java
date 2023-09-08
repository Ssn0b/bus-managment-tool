package com.snob.busmanagmenttool.model.entity.route;

import com.snob.busmanagmenttool.model.entity.machinery.Bus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity(name = "Trip")
@Table(name = "_trip")
public class Trip {
    @Id
    @GeneratedValue
    private UUID id;
    private String tripNumber;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    @ManyToOne
    @JoinColumn(name = "start_stop_id", nullable = false)
    private Stop startStop;
    @ManyToOne
    @JoinColumn(name = "finish_stop_id", nullable = false)
    private Stop finishStop;
    @OneToOne
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;
    private Double ticketPrice;

}
