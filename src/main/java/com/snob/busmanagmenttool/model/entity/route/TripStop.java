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
@Entity(name = "TripStop")
@Table(name = "trip_stop")
public class TripStop {
    @EmbeddedId
    private TripStopId id;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tripId")
    private Trip trip;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("stopId")
    private Stop stop;
}
