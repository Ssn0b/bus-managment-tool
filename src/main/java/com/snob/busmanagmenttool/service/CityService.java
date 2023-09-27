package com.snob.busmanagmenttool.service;

import com.snob.busmanagmenttool.exception.EntityNotFoundException;
import com.snob.busmanagmenttool.model.dto.CityDTO;
import com.snob.busmanagmenttool.model.entity.route.City;
import com.snob.busmanagmenttool.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}
