package com.snob.busmanagmenttool.service;

import com.snob.busmanagmenttool.exception.EntityNotFoundException;
import com.snob.busmanagmenttool.model.dto.StopDTO;
import com.snob.busmanagmenttool.model.entity.route.Stop;
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
    private final StopRepository repository;
    private final ModelMapper modelMapper;

    public Stop saveStop(StopDTO stop){
        return repository.save(modelMapper.map(stop, Stop.class));
    }

    public List<StopDTO> getAllStops(){
        TypeMap<Stop, StopDTO> typeMap = modelMapper.createTypeMap(Stop.class, StopDTO.class);
        typeMap.addMappings(mapper -> {
            mapper.map(src -> src.getCity().getId(), StopDTO::setCityId);
        });
        return repository.findAll()
                .stream()
                .map(stop->modelMapper.map(stop,StopDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<StopDTO> getStopById(Long id){
        Stop stop = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Stop with ID " +
                id + " not found."));
        return Optional.ofNullable(modelMapper.map(stop, StopDTO.class));
    }
    public void deleteStopById(Long id){
        Stop stop = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Stop with ID " +
                id + " not found."));
        repository.deleteById(id);
    }
}
