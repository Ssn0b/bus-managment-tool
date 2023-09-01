package com.snob.busmanagmenttool.model.entity.route;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class TripStopId implements Serializable {
    @Column(name = "trip_id")
    private Long tripId;
    @Column(name = "stop_id")
    private Long stopId;
}
