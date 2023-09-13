package com.snob.busmanagmenttool.service;

import com.snob.busmanagmenttool.exception.BusAlreadyHasRouteException;
import com.snob.busmanagmenttool.exception.EntityNotFoundException;
import com.snob.busmanagmenttool.model.dto.BusDTO;
import com.snob.busmanagmenttool.model.dto.TicketDTO;
import com.snob.busmanagmenttool.model.dto.TripDTO;
import com.snob.busmanagmenttool.model.entity.machinery.Bus;
import com.snob.busmanagmenttool.model.entity.route.*;
import com.snob.busmanagmenttool.model.entity.route.ticket.Ticket;
import com.snob.busmanagmenttool.model.entity.route.ticket.TicketStatus;
import com.snob.busmanagmenttool.repository.BusRepository;
import com.snob.busmanagmenttool.repository.TicketRepository;
import com.snob.busmanagmenttool.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class TripService {
    private final TripRepository tripRepository;
    private final BusRepository busRepository;
    private final TicketRepository ticketRepository;

    private final TicketService ticketService;
    private final ModelMapper modelMapper;
    public void saveTrip(TripDTO tripDTO){
        Optional<Bus> busOptional = busRepository.findById(tripDTO.getBusId());

        if (busOptional.isPresent()) {
            Bus bus = busOptional.get();
            if (tripRepository.existsRouteByBusId(tripDTO.getBusId())) {
                throw new BusAlreadyHasRouteException("Bus with ID " + tripDTO.getBusId() + " is already associated with an active route.");
            } else {
                Trip trip = modelMapper.map(tripDTO, Trip.class);
                Trip newTrip = tripRepository.save(trip);
                int numberOfSeats = bus.getSeats();
                List<Ticket> ticketDTOS = IntStream.range(0, numberOfSeats).mapToObj(seatNumber -> {
                            TicketDTO ticketDTO = new TicketDTO();
                            ticketDTO.setTicketNumber(ticketService.generateRandomTicketNumber());
                            ticketDTO.setUserId(null);
                            ticketDTO.setSeatNumber(seatNumber + 1);
                            ticketDTO.setStatus(TicketStatus.UNSOLD);
                            ticketDTO.setTripId(newTrip.getId());
                            return ticketDTO;
                        })
                        .map(ticketDTO->modelMapper.map(ticketDTO, Ticket.class))
                        .toList();

                ticketRepository.saveAll(ticketDTOS);
               // tripRepository.save(newTrip);
        }
        } else {
            throw new EntityNotFoundException("Bus with ID " + tripDTO.getBusId() + " is not exist.");
        }
    }

    public List<TripDTO> getAllTrips(){
        TypeMap<Trip, TripDTO> typeMap = modelMapper.createTypeMap(Trip.class, TripDTO.class);
        typeMap.addMappings(mapper -> {
            mapper.map(src -> src.getBus().getId(), TripDTO::setBusId);
            mapper.map(src -> src.getStartStop().getId(), TripDTO::setStartStopId);
            mapper.map(src -> src.getFinishStop().getId(), TripDTO::setFinishStopId);
        });
        return tripRepository.findAll()
                .stream()
                .map(trip->modelMapper.map(trip,TripDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<TripDTO> getTripById(UUID id){
        Trip trip = tripRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Trip with ID " +
                id + " not found."));
        return Optional.ofNullable(modelMapper.map(trip, TripDTO.class));
    }
    public void deleteTripById(UUID id){
        if (tripRepository.existsById(id)) {
            tripRepository.deleteById(id);
        }else {
            throw new EntityNotFoundException("Trip with ID " +
                    id + " not found.");
        }
    }
}
