package com.snob.busmanagmenttool.service;

import com.snob.busmanagmenttool.exception.BusAlreadyHasRouteException;
import com.snob.busmanagmenttool.exception.EntityNotFoundException;
import com.snob.busmanagmenttool.model.dto.BusDTO;
import com.snob.busmanagmenttool.model.dto.TicketDTO;
import com.snob.busmanagmenttool.model.dto.TripDTO;
import com.snob.busmanagmenttool.model.dto.TripInfoForTicketDTO;
import com.snob.busmanagmenttool.model.entity.machinery.Bus;
import com.snob.busmanagmenttool.model.entity.route.*;
import com.snob.busmanagmenttool.model.entity.route.ticket.Ticket;
import com.snob.busmanagmenttool.model.entity.route.ticket.TicketStatus;
import com.snob.busmanagmenttool.repository.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

@Service
public class TripService {
  private final TripRepository tripRepository;
  private final BusRepository busRepository;
  private final TicketRepository ticketRepository;
  private final StopRepository stopRepository;
  private final TicketService ticketService;
  private final ModelMapper modelMapper;
  public TripService(
      TripRepository tripRepository,
      TicketRepository ticketRepository,
      BusRepository busRepository,
      StopRepository stopRepository,
      TicketService ticketService,
      ModelMapper modelMapper) {
    this.tripRepository = tripRepository;
    this.ticketRepository = ticketRepository;
    this.busRepository = busRepository;
    this.stopRepository = stopRepository;
    this.modelMapper = modelMapper;
    this.ticketService = ticketService;
  }

  public void saveTrip(TripDTO tripDTO) {
    Bus bus =
        busRepository
            .findById(tripDTO.getBus().getId())
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "Bus with ID " + tripDTO.getBus().getId() + " not found."));
    Stop startStop =
        stopRepository
            .findById(tripDTO.getStartStop().getId())
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "Stop with id " + tripDTO.getStartStop().getId() + " not found."));
    Stop finalStop =
        stopRepository
            .findById(tripDTO.getFinishStop().getId())
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "Stop with id " + tripDTO.getFinishStop().getId() + " not found."));
    ;
    if (tripRepository.existsRouteByBusId(tripDTO.getBus().getId())
        || tripRepository.existsTripByBusId(tripDTO.getBus().getId())) {
      throw new BusAlreadyHasRouteException(
          "Bus with ID " + tripDTO.getBus().getId() + " is already associated with an active route.");
    } else {
      Trip trip = modelMapper.map(tripDTO, Trip.class);
      trip.setStartStop(startStop);
      trip.setFinishStop(finalStop);
      trip.setBus(bus);
      Trip newTrip = tripRepository.save(trip);
      int numberOfSeats = bus.getSeats();
      List<Ticket> ticketDTOS =
          IntStream.range(0, numberOfSeats)
              .mapToObj(
                  seatNumber ->
                      TicketDTO.builder()
                          .ticketNumber(ticketService.generateRandomTicketNumber())
                          .seatNumber(seatNumber + 1)
                          .status(TicketStatus.UNSOLD)
                          .trip(modelMapper.map(newTrip, TripDTO.class))
                          .build())
              .map(ticketDTO -> modelMapper.map(ticketDTO, Ticket.class))
              .toList();
      ticketRepository.saveAll(ticketDTOS);
    }
  }

  public List<TripDTO> getAllTrips() {
    return tripRepository.findAll().stream()
        .map(trip -> modelMapper.map(trip, TripDTO.class))
        .collect(Collectors.toList());
  }

  public Optional<TripDTO> getTripById(UUID id) {
    Trip trip =
        tripRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Trip with ID " + id + " not found."));
    return Optional.ofNullable(modelMapper.map(trip, TripDTO.class));
  }

  public Optional<TripDTO> findByTripNumber(String tripNum) {
    Trip trip =
        tripRepository
            .findByTripNumber(tripNum)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "Trip with " + "trip number " + tripNum + " not found."));
    return Optional.ofNullable(modelMapper.map(trip, TripDTO.class));
  }

  public void deleteTripById(UUID id) {
    if (tripRepository.existsById(id)) {
      tripRepository.deleteById(id);
    } else {
      throw new EntityNotFoundException("Trip with ID " + id + " not found.");
    }
  }
}
