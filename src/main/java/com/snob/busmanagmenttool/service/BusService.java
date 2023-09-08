package com.snob.busmanagmenttool.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snob.busmanagmenttool.exception.DriverAlreadyHasBusException;
import com.snob.busmanagmenttool.exception.EntityNotFoundException;
import com.snob.busmanagmenttool.exception.UserIsNotDriverException;
import com.snob.busmanagmenttool.model.dto.BusDTO;
import com.snob.busmanagmenttool.model.entity.machinery.Bus;
import com.snob.busmanagmenttool.model.entity.machinery.BusStatus;
import com.snob.busmanagmenttool.model.entity.user.Role;
import com.snob.busmanagmenttool.model.entity.user.User;
import com.snob.busmanagmenttool.repository.BusRepository;

import java.io.IOException;
import java.sql.Driver;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.snob.busmanagmenttool.repository.user.UserRepository;
import com.snob.busmanagmenttool.service.aws.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Slf4j
@Service
@RequiredArgsConstructor
public class BusService {
    private final BusRepository busRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final S3Service s3Service;
    private final ObjectMapper objectMapper;

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
    public Optional<BusDTO> getBusById(UUID id){
        Bus bus = busRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bus with ID " +
            id + " not found."));
        return Optional.ofNullable(modelMapper.map(bus, BusDTO.class));
    }
    public void saveBus(MultipartFile file, String busData){
        try{
        BusDTO bus = objectMapper.readValue(busData, BusDTO.class);
        UUID driverId = bus.getDriverId();
        User driver = userRepository.findById(driverId)
                .orElseThrow(() -> new EntityNotFoundException("Driver with ID " +
                        driverId + " not found."));
        if (busRepository.existsBusByDriverId(driverId)) {
            throw new DriverAlreadyHasBusException("Driver with ID " +
                    driverId + " is already associated with an active bus.");
        }else if(driver.getRole() != Role.DRIVER){
            throw new UserIsNotDriverException(
                    "User with ID " + driverId +
                            " is not working as driver.");
        }
        else {
            String photoUrl = s3Service.uploadBusImage(file);
            bus.setPhotoUrl(photoUrl);
            busRepository.save(modelMapper.map(bus,Bus.class));
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ResponseEntity<String> updateBus(UUID id, MultipartFile file, String updatedFields){
        try {
            Optional<Bus> optionalBus = busRepository.findById(id);
            if (optionalBus.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bus not found.");
            }
            Bus existingBus = optionalBus.get();
            if (file != null && !file.isEmpty()) {
                s3Service.deleteFile(existingBus.getPhotoUrl());
                String photoUrl = s3Service.uploadBusImage(file);
                existingBus.setPhotoUrl(photoUrl);
            }
            if (updatedFields != null && !updatedFields.isEmpty()) {
                BusDTO updatedBus = objectMapper.readValue(updatedFields, BusDTO.class);
                if (updatedBus.getSeats() != null) {
                    existingBus.setSeats(updatedBus.getSeats());
                }
                if (updatedBus.getCarNumber() != null) {
                    existingBus.setCarNumber(updatedBus.getCarNumber());
                }
                if (updatedBus.getDriverId() != null) {
                    UUID updatedDriverId = updatedBus.getDriverId();
                    User updatedDriver = userRepository.findById(updatedDriverId)
                            .orElseThrow(() -> new EntityNotFoundException("Driver with ID " + updatedDriverId + " not found."));
                    existingBus.setDriver(updatedDriver);
                }
                if (updatedBus.getBusStatus() != null) {
                    existingBus.setBusStatus(updatedBus.getBusStatus());
                }
                busRepository.save(existingBus);
            }
            return ResponseEntity.ok("Bus updated successfully.");

        } catch (Exception  e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update bus.");
        }
    }
    public void deleteBusById(UUID id){
        try {
        Bus bus = busRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bus with ID " +
                id + " not found."));
        s3Service.deleteFile(bus.getPhotoUrl());
        busRepository.deleteById(id);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
