package com.snob.busmanagmenttool.service;

import com.snob.busmanagmenttool.exception.BusSeatIsAlreadyTaken;
import com.snob.busmanagmenttool.exception.DriverAlreadyHasBusException;
import com.snob.busmanagmenttool.exception.EntityNotFoundException;
import com.snob.busmanagmenttool.model.dto.TicketDTO;
import com.snob.busmanagmenttool.model.entity.route.ticket.Ticket;
import com.snob.busmanagmenttool.model.entity.route.ticket.TicketStatus;
import com.snob.busmanagmenttool.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final ModelMapper modelMapper;

    public List<TicketDTO> getAllTickets() {
        return ticketRepository.findAll()
                .stream()
                .map(tickets -> modelMapper.map(tickets,TicketDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<TicketDTO> getTicketById(String id) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Ticket" +
                " with ID " + id + "not found."));
        return Optional.ofNullable(modelMapper.map(ticket,TicketDTO.class));
    }

    public void createTicket(TicketDTO ticketDTO) {
        if (ticketRepository.existsTicketBySeatNumber(ticketDTO.getSeatNumber())) {
            throw new BusSeatIsAlreadyTaken("Seat with number " +
                    ticketDTO.getSeatNumber() + " is already taken.");
        }else {
            ticketRepository.save(modelMapper.map(ticketDTO,Ticket.class));
        }
    }

    public Ticket updateTicket(String id, Map<String, Object> updatedFields) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket with ID " + id + " not found."));

        TicketDTO ticketDTO = modelMapper.map(ticket, TicketDTO.class);

        if (updatedFields.containsKey("userId")) {
            ticketDTO.setUserId((UUID) updatedFields.get("userId"));
        }
        if (updatedFields.containsKey("seatNumber")) {
            if (ticketRepository.existsTicketBySeatNumber((int) updatedFields.get("seatNumber"))) {
                throw new BusSeatIsAlreadyTaken("Seat with number " +
                        ticketDTO.getSeatNumber() + " is already taken.");
            }
            ticketDTO.setSeatNumber((int) updatedFields.get("seatNumber"));
        }
        if (updatedFields.containsKey("status")) {
            ticketDTO.setStatus((TicketStatus) updatedFields.get("status"));
        }
        Ticket updatedTicket = modelMapper.map(ticketDTO, Ticket.class);
        return ticketRepository.save(updatedTicket);
    }

    public void deleteTicket(String id) {
        ticketRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Ticket with ID " +
                id + " not found."));
        ticketRepository.deleteById(id);
    }
    public void updateTicketStatus() {
        List<Ticket> tickets = ticketRepository.findByStatusNot(TicketStatus.EXPIRED);
        LocalDateTime now = LocalDateTime.now();
        for (Ticket ticket : tickets) {
            if (ticket.getTrip().getDepartureTime().isBefore(now)) {
                ticket.setStatus(TicketStatus.EXPIRED);
                ticketRepository.save(ticket);
            }
        }
    }
    @Scheduled(fixedRate = 300000) // 5 minutes in milliseconds
    public void checkTicketArrivalTime() {
        updateTicketStatus();
    }

    public String generateRandomTicketNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(1000000);
        String formattedNumber = String.format("%06d", randomNumber);
        return "TICKET-" + formattedNumber;
    }
}
