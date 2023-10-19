package com.snob.busmanagmenttool.service;

import com.snob.busmanagmenttool.exception.EntityNotFoundException;
import com.snob.busmanagmenttool.model.dto.BusDTO;
import com.snob.busmanagmenttool.model.dto.CityDTO;
import com.snob.busmanagmenttool.model.entity.machinery.Bus;
import com.snob.busmanagmenttool.model.entity.route.City;
import com.snob.busmanagmenttool.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;
    public City saveCity(CityDTO cityDTO){
        return cityRepository.save(modelMapper.map(cityDTO, City.class));
    }
    public Optional<CityDTO> findCityById(Long id){
        City city = cityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("City with ID " +
                id + " not found."));
        return Optional.ofNullable(modelMapper.map(city, CityDTO.class));
    }
    public List<CityDTO> getAllCities(){
        return cityRepository.findAll()
                .stream()
                .map(city->modelMapper.map(city,CityDTO.class))
                .collect(Collectors.toList());
    }
}
