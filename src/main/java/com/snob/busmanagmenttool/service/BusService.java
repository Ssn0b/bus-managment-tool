package com.snob.busmanagmenttool.service;

import com.snob.busmanagmenttool.model.dto.BusDTO;
import com.snob.busmanagmenttool.model.entity.Bus;
import com.snob.busmanagmenttool.repository.BusRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BusService {
    private final BusRepository busRepository;
    private final ModelMapper modelMapper;


    public List<BusDTO> getAllBuses(){
        TypeMap<Bus, BusDTO> typeMap = modelMapper.createTypeMap(Bus.class, BusDTO.class);
        typeMap.addMappings(mapper -> {
            mapper.map(src -> src.getDriver().getId(), BusDTO::setDriverId);
        });
        return busRepository.findAll()
                .stream()
                .map(bus->modelMapper.map(bus,BusDTO.class))
                .collect(Collectors.toList());
    }

    public BusDTO getBusById(Long id){
        Optional<Bus> bus = busRepository.findById(id);
        return modelMapper.map(bus, BusDTO.class);
    }

    public Bus saveBus(Bus bus){
        return busRepository.save(bus);
    }
}
