package com.snob.busmanagmenttool.service;

import com.snob.busmanagmenttool.exception.DriverAlreadyHasBusException;
import com.snob.busmanagmenttool.exception.EntityNotFoundException;
import com.snob.busmanagmenttool.model.dto.BusDTO;
import com.snob.busmanagmenttool.model.entity.machinery.Bus;
import com.snob.busmanagmenttool.repository.BusRepository;
import java.util.List;
import java.util.Map;
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

    public Optional<BusDTO> getBusById(Long id){
        Bus bus = busRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bus with ID " +
            id + " not found."));
        return Optional.ofNullable(modelMapper.map(bus, BusDTO.class));
    }

    public void saveBus(BusDTO bus){
        Long driverId = bus.getDriverId();
        if (busRepository.existsBusByDriverId(driverId)) {
            throw new DriverAlreadyHasBusException("Driver with ID " +
                    driverId + " is already associated with an active bus.");
        }else {
            busRepository.save(modelMapper.map(bus,Bus.class));
        }
    }

    public Bus updateBus(Long id, Map<String,Object> updatedFields){
        Bus bus = busRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bus with ID " +
                id + " not found."));
        BusDTO busDTO = modelMapper.map(bus,BusDTO.class);
        if (updatedFields.containsKey("brand")) {
            busDTO.setBrand((String) updatedFields.get("brand"));
        }
        if (updatedFields.containsKey("seats")) {
            busDTO.setSeats((int) updatedFields.get("seats"));
        }
        if (updatedFields.containsKey("carNumber")) {
            busDTO.setCarNumber((String) updatedFields.get("carNumber"));
        }
        if (updatedFields.containsKey("driverId")) {
            busDTO.setDriverId((Long) updatedFields.get("driverId"));
        }
        if (updatedFields.containsKey("active")) {
            busDTO.setActive((boolean) updatedFields.get("active"));
        }

        return busRepository.save(modelMapper.map(busDTO,Bus.class));
    }

    public void deleteBusById(Long id){
        Bus bus = busRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bus with ID " +
                id + " not found."));
        busRepository.deleteById(id);
    }
}
