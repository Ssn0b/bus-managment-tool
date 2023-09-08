package com.snob.busmanagmenttool.model.entity.route.ticket;

import com.snob.busmanagmenttool.model.entity.machinery.Bus;
import com.snob.busmanagmenttool.model.entity.route.City;
import com.snob.busmanagmenttool.model.entity.route.Trip;
import com.snob.busmanagmenttool.model.entity.route.ticket.TicketStatus;
import com.snob.busmanagmenttool.model.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "ticket")
public class Ticket {
    @Id
    private String id;
    private String ticketNumber;
    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private int seatNumber;
    @Enumerated(EnumType.STRING)
    private TicketStatus status;
}
