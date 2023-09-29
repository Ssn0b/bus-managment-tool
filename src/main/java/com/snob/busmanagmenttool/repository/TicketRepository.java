package com.snob.busmanagmenttool.repository;

import com.snob.busmanagmenttool.model.dto.TicketDTO;
import com.snob.busmanagmenttool.model.entity.route.Trip;
import com.snob.busmanagmenttool.model.entity.route.ticket.Ticket;
import com.snob.busmanagmenttool.model.entity.route.ticket.TicketStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String> {
    List<Ticket> findByStatusNot(TicketStatus status);
    List<Ticket> findByTripId(UUID trip_id);
    List<TicketDTO> findByTrip_TripNumberAndSeatNumberIn(String trip_tripNumber, Collection<Integer> seatNumber);
    boolean existsTicketBySeatNumber(int seatNumber);

}
