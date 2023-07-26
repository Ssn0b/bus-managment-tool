package com.snob.busmanagmenttool.service.user;

import com.snob.busmanagmenttool.exception.EntityNotFoundException;
import com.snob.busmanagmenttool.model.dto.BusDTO;
import com.snob.busmanagmenttool.model.dto.BusDriverDTO;
import com.snob.busmanagmenttool.model.entity.machinery.Bus;
import com.snob.busmanagmenttool.model.entity.user.BusDriver;
import com.snob.busmanagmenttool.repository.BusRepository;
import com.snob.busmanagmenttool.repository.user.BusDriverRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BusDriverService {
    private final BusDriverRepository busDriverRepository;
    private final BusRepository busRepository;
    private final ModelMapper modelMapper;

    public Optional<BusDTO> getCurrentBusById(Long driverId) {
        Bus bus = busRepository.findBusByDriverId(driverId).orElseThrow(() -> new EntityNotFoundException("Driver with ID " +
                driverId + " does not have any assigned bus."));
        return Optional.ofNullable(modelMapper.map(bus, BusDTO.class));
    }

    public Optional<BusDriverDTO> getBusDriverByUsername(String username){
        BusDriver busDriver = busDriverRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("Bus driver with username " +
                username + " not found."));
        return Optional.ofNullable(modelMapper.map(busDriver, BusDriverDTO.class));
    }
    @Transactional
    public void deleteBusDriverById(Long driverId){
        busDriverRepository.findById(driverId).orElseThrow(() -> new EntityNotFoundException("Bus driver with id " +
                driverId + " not found."));
        busRepository.updateDriverIdToNull(driverId);
        busDriverRepository.deleteById(driverId);
    }

    public BusDriver updateBusDriver(String username, Map<String,Object> updatedFields){
        BusDriver busDriver = busDriverRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("Driver with username " +
                username + " not found."));
        BusDriverDTO busDriverDTO = modelMapper.map(busDriver,BusDriverDTO.class);
        if (updatedFields.containsKey("experience")) {
            busDriverDTO.setWorkExperience((int) updatedFields.get("experience"));
        }
        return busDriverRepository.save(modelMapper.map(busDriverDTO,BusDriver.class));
    }
}
