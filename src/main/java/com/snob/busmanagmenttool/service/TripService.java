package com.snob.busmanagmenttool.service;

import com.snob.busmanagmenttool.exception.BusAlreadyHasRouteException;
import com.snob.busmanagmenttool.exception.EntityNotFoundException;
import com.snob.busmanagmenttool.model.dto.TripDTO;
import com.snob.busmanagmenttool.model.entity.route.*;
import com.snob.busmanagmenttool.repository.StopRepository;
import com.snob.busmanagmenttool.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TripService {
    private final TripRepository tripRepository;
    private final StopRepository stopRepository;
    private final ModelMapper modelMapper;
    public void saveTrip(TripDTO tripDTO){
        Long busId = tripDTO.getBusId();

        if (tripRepository.existsRouteByBusId(busId)) {
            throw new BusAlreadyHasRouteException("Bus with ID " + busId + " is already associated with an active route.");
        } else {
            Trip trip = modelMapper.map(tripDTO, Trip.class);

            List<TripStop> tripStops = new ArrayList<>();
            for (Long stopId : tripDTO.getStopIds()) {
                Stop stop = stopRepository.findById(stopId)
                        .orElseThrow(()->new EntityNotFoundException("Stop with ID " +
                                stopId + " not found."));
            TripStopId tripStopId = new TripStopId(trip.getId(), stop.getId());

            TripStop tripStop = new TripStop();
                tripStop.setId(tripStopId);
                tripStop.setTrip(trip);
                tripStop.setStop(stop);

                tripStops.add(tripStop);
            }
            trip.setTripStops(tripStops);

            tripRepository.save(trip);
        }
    }

    public List<TripDTO> getAllTrips(){
        TypeMap<Trip, TripDTO> typeMap = modelMapper.createTypeMap(Trip.class, TripDTO.class);
        typeMap.addMappings(mapper -> {
            mapper.map(src -> src.getBus().getId(), TripDTO::setBusId);
            mapper.map(src -> src.getTripStops()
                    .stream()
                    .map(TripStop::getId)
                    .collect(Collectors.toList()), TripDTO::setStopIds);
        });
        return tripRepository.findAll()
                .stream()
                .map(trip->modelMapper.map(trip,TripDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<TripDTO> getTripById(Long id){
        Trip trip = tripRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Trip with ID " +
                id + " not found."));
        return Optional.ofNullable(modelMapper.map(trip, TripDTO.class));
    }
    public void deleteTripById(Long id){
        if (tripRepository.existsById(id)) {
            tripRepository.deleteById(id);
        }else {
            throw new EntityNotFoundException("Trip with ID " +
                    id + " not found.");
        }
    }
}
