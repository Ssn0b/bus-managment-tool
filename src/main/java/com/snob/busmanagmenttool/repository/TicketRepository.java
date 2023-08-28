package com.snob.busmanagmenttool.repository;

import com.snob.busmanagmenttool.model.entity.route.ticket.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String> {}
