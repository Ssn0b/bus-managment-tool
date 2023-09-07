package com.snob.busmanagmenttool.service;

import com.snob.busmanagmenttool.exception.BusAlreadyHasRouteException;
import com.snob.busmanagmenttool.exception.EntityNotFoundException;
import com.snob.busmanagmenttool.model.dto.RouteDTO;
import com.snob.busmanagmenttool.model.entity.route.Route;
import com.snob.busmanagmenttool.model.entity.route.RouteStop;
import com.snob.busmanagmenttool.model.entity.route.RouteStopId;
import com.snob.busmanagmenttool.model.entity.route.Stop;
import com.snob.busmanagmenttool.repository.RouteRepository;
import com.snob.busmanagmenttool.repository.StopRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RouteService {
    private final RouteRepository routeRepository;
    private final StopRepository stopRepository;
    private final ModelMapper modelMapper;

    public void saveRoute(RouteDTO routeDTO){
        Long busId = routeDTO.getBusId();
        if (routeRepository.existsRouteByBusId(busId)) {
            throw new BusAlreadyHasRouteException("Bus with ID " + busId + " is already associated with an active route.");
        } else {
            Route route = modelMapper.map(routeDTO, Route.class);

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

            routeRepository.save(route);
        }
    }

    public List<RouteDTO> getAllRoutes(){
        TypeMap<Route, RouteDTO> typeMap = modelMapper.createTypeMap(Route.class, RouteDTO.class);
        typeMap.addMappings(mapper -> {
            mapper.map(src -> src.getBus().getId(), RouteDTO::setBusId);
            mapper.map(src -> src.getRouteStops()
                    .stream()
                    .map(x -> x.getId())
                    .collect(Collectors.toList()), RouteDTO::setStopIds);
        });
        return routeRepository.findAll()
                .stream()
                .map(route->modelMapper.map(route,RouteDTO.class))
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
}
