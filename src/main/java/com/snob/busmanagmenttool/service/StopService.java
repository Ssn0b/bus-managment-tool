package com.snob.busmanagmenttool.service;

import com.snob.busmanagmenttool.exception.EntityNotFoundException;
import com.snob.busmanagmenttool.model.dto.StopDTO;
import com.snob.busmanagmenttool.model.entity.route.City;
import com.snob.busmanagmenttool.model.entity.route.Stop;
import com.snob.busmanagmenttool.repository.CityRepository;
import com.snob.busmanagmenttool.repository.StopRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StopService {
  private final CityRepository cityRepository;
  private final StopRepository repository;
  private final ModelMapper modelMapper;

  public Stop saveStop(StopDTO stopDTO) {
    City city =
        cityRepository
            .findById(stopDTO.getCity().getId())
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "City with ID " + stopDTO.getCity().getId() + " not found."));
    Stop newStop = new Stop();
    newStop.setCity(city);
    newStop.setStreet(stopDTO.getStreet());
    return repository.save(newStop);
  }

  public List<StopDTO> getAllStops() {
    TypeMap<Stop, StopDTO> typeMap = modelMapper.createTypeMap(Stop.class, StopDTO.class);
    typeMap.addMappings(
        mapper -> {
          mapper.map(Stop::getCity, StopDTO::setCity);
        });
    return repository.findAll().stream()
        .map(stop -> modelMapper.map(stop, StopDTO.class))
        .collect(Collectors.toList());
  }

  public Optional<StopDTO> getStopById(Long id) {
    Stop stop =
        repository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Stop with ID " + id + " not found."));
    return Optional.ofNullable(modelMapper.map(stop, StopDTO.class));
  }

  public void deleteStopById(Long id) {
    Stop stop =
        repository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Stop with ID " + id + " not found."));
    repository.deleteById(id);
  }
}
