package com.snob.busmanagmenttool.service;

import com.snob.busmanagmenttool.exception.BusAlreadyHasRouteException;
import com.snob.busmanagmenttool.exception.EntityNotFoundException;
import com.snob.busmanagmenttool.model.dto.RouteDTO;
import com.snob.busmanagmenttool.model.entity.machinery.Bus;
import com.snob.busmanagmenttool.model.entity.route.Route;
import com.snob.busmanagmenttool.model.entity.route.RouteStop;
import com.snob.busmanagmenttool.model.entity.route.RouteStopId;
import com.snob.busmanagmenttool.model.entity.route.Stop;
import com.snob.busmanagmenttool.model.entity.user.User;
import com.snob.busmanagmenttool.repository.BusRepository;
import com.snob.busmanagmenttool.repository.RouteRepository;
import com.snob.busmanagmenttool.repository.StopRepository;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

@Service
public class RouteService {
    private final RouteRepository routeRepository;
    private final StopRepository stopRepository;
    private final BusRepository busRepository;
    private final ModelMapper modelMapper;
    private final TypeMap<Route, RouteDTO> routeTypeMap;

    public RouteService(RouteRepository routeRepository, StopRepository stopRepository,
                        BusRepository busRepository, ModelMapper modelMapper) {
        this.routeRepository = routeRepository;
        this.stopRepository = stopRepository;
        this.busRepository = busRepository;
        this.modelMapper = modelMapper;

        this.routeTypeMap = modelMapper.createTypeMap(Route.class, RouteDTO.class);
        this.routeTypeMap.addMappings(mapper -> {
            mapper.map(src -> src.getBus().getId(), RouteDTO::setBusId);
        });
    }

    public void saveRoute(RouteDTO routeDTO){
        UUID busId = routeDTO.getBusId();
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new EntityNotFoundException("Bus with ID " +
                        busId + " not found."));
        if (routeRepository.existsRouteByBusId(busId)) {
            throw new BusAlreadyHasRouteException("Bus with ID " + busId + " is already associated with an active route.");
        } else {
            Route route = new Route();
            List<RouteStop> routeStops = new ArrayList<>();
            for (Long stopId : routeDTO.getStopIds()) {
                Stop stop = stopRepository.findById(stopId)
                        .orElseThrow(()->new EntityNotFoundException("Stop with ID " +
                                stopId + " not found."));
                RouteStopId routeStopId = new RouteStopId(route.getId(), stop.getId());

                RouteStop routeStop = new RouteStop();
                routeStop.setId(routeStopId);
                routeStop.setRoute(route);
                routeStop.setStop(stop);

                routeStops.add(routeStop);
            }
            route.setRouteStops(routeStops);
            route.setName(routeDTO.getName());
            route.setDuration(Duration.parse(routeDTO.getDuration()));
            route.setDistance(routeDTO.getDistance());
            route.setRouteNumber(routeDTO.getRouteNumber());
            route.setBus(bus);
            routeRepository.save(route);
        }
    }

    public List<RouteDTO> getAllRoutes(){
        return routeRepository.findAll()
                .stream()
                .map(route -> {
                    RouteDTO routeDTO = routeTypeMap.map(route);
                    routeDTO.setDuration(formatDuration(route.getDuration()));
                    return routeDTO;
                })
                .collect(Collectors.toList());
    }

    public Optional<RouteDTO> getRouteById(Long id){
        Route route = routeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Route with ID " +
                id + " not found."));
        return Optional.ofNullable(modelMapper.map(route, RouteDTO.class));
    }
    public void deleteRouteById(Long id){
        if (routeRepository.existsById(id)) {
            routeRepository.deleteById(id);
        }else {
            throw new EntityNotFoundException("Route with ID " +
                    id + " not found.");
        }
    }

    private String formatDuration(Duration duration) {
        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        StringBuilder formattedDuration = new StringBuilder();

        if (days > 0) {
            formattedDuration.append(days).append(" days ");
        }
        if (hours > 0) {
            formattedDuration.append(hours).append(" hours ");
        }
        if (minutes > 0) {
            formattedDuration.append(minutes).append(" minutes ");
        }
        if (seconds > 0) {
            formattedDuration.append(seconds).append(" seconds");
        }

        return formattedDuration.toString().trim();
    }
}
