package com.snob.busmanagmenttool.service;

import com.snob.busmanagmenttool.exception.BusSeatIsAlreadyTaken;
import com.snob.busmanagmenttool.exception.DriverAlreadyHasBusException;
import com.snob.busmanagmenttool.exception.EntityNotFoundException;
import com.snob.busmanagmenttool.model.dto.TicketDTO;
import com.snob.busmanagmenttool.model.entity.route.ticket.Ticket;
import com.snob.busmanagmenttool.model.entity.route.ticket.TicketStatus;
import com.snob.busmanagmenttool.model.entity.user.User;
import com.snob.busmanagmenttool.repository.TicketRepository;
import com.snob.busmanagmenttool.repository.user.UserRepository;
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
    private final UserRepository userRepository;

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

    public Ticket updateTicketStatus(String ticketId, UUID userId, TicketStatus newStatus) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException("Ticket with ID " + ticketId + " not found."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found."));

        switch (newStatus) {
            case BOOKED -> {
                if (ticket.getStatus() == TicketStatus.UNSOLD) {
                    ticket.setStatus(TicketStatus.BOOKED);
                    ticket.setUser(user);
                } else {
                    throw new IllegalStateException("Ticket with ID " + ticketId + " cannot be booked.");
                }
            }
            case VALID -> {
                if (ticket.getStatus() == TicketStatus.UNSOLD || ticket.getStatus() == TicketStatus.BOOKED) {
                    if (user.getBalance() < ticket.getTrip().getTicketPrice()) {
                        throw new IllegalStateException("User with ID " + userId + " does not have enough resources in balance.");
                    }
                    user.setBalance(user.getBalance() - ticket.getTrip().getTicketPrice());
                    ticket.setStatus(TicketStatus.VALID);
                    ticket.setUser(user);
                    userRepository.save(user);
                } else {
                    throw new IllegalStateException("Ticket with ID " + ticketId + " cannot be bought.");
                }
            }
            case CANCELED -> {
                if (ticket.getStatus() == TicketStatus.VALID) {
                    ticket.setStatus(TicketStatus.CANCELED);
                } else {
                    throw new IllegalStateException("Ticket with ID " + ticketId + " cannot be canceled.");
                }
            }
            case UNSOLD -> {
                if (ticket.getStatus() == TicketStatus.CANCELED) {
                    user.setBalance(user.getBalance() + (ticket.getTrip().getTicketPrice()) / 2);
                    ticket.setStatus(TicketStatus.VALID);
                    ticket.setUser(user);
                    userRepository.save(user);
                } else if (ticket.getStatus() == TicketStatus.BOOKED) {
                    ticket.setStatus(TicketStatus.VALID);
                } else {
                    throw new IllegalStateException("Ticket with ID " + ticketId + " cannot be bought.");
                }
            }
            default -> throw new IllegalArgumentException("Invalid ticket status: " + newStatus);
        }

        return ticketRepository.save(ticket);
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
            if (ticket.getTrip() != null) {
                if (ticket.getTrip().getDepartureTime().isBefore(now)) {
                    ticket.setStatus(TicketStatus.EXPIRED);
                    ticketRepository.save(ticket);
                }
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
