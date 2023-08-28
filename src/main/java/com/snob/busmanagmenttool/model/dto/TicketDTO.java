package com.snob.busmanagmenttool.model.dto;

import com.snob.busmanagmenttool.model.entity.route.ticket.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TicketDTO {
    private String id;
    private String ticketNumber;
    private int userId;
    private int busId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private int seatNumber;
    private TicketStatus status;
    private Double ticketPrice;
}
