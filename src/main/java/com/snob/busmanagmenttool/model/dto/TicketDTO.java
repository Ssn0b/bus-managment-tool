package com.snob.busmanagmenttool.model.dto;

import com.snob.busmanagmenttool.model.entity.route.ticket.TicketStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Validated
public class TicketDTO {
    private String id;
    @NotNull(message  = "Ticket number is required")
    @Pattern(regexp = "TICKET-[0-9]{1,6}", message = "Invalid ticket number format")
    private String ticketNumber;
    @NotNull(message  = "User id is required")
    @Positive(message = "User ID must be a positive integer")
    private int userId;
    @NotNull(message  = "Seat number is required")
    @Positive(message = "Seat number must be a positive integer")
    private int seatNumber;
    @NotNull(message  = "Ticket status is required")
    private TicketStatus status;
    @NotNull(message  = "Ticket price is required")
    @Positive(message = "Ticket price must be a positive value")
    private Double ticketPrice;
}
