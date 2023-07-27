package com.snob.busmanagmenttool.service;

import com.snob.busmanagmenttool.model.dto.CityDTO;
import com.snob.busmanagmenttool.model.dto.StopDTO;
import com.snob.busmanagmenttool.model.entity.route.City;
import com.snob.busmanagmenttool.model.entity.route.Stop;
import com.snob.busmanagmenttool.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;
    public City saveCity(CityDTO cityDTO){
        return cityRepository.save(modelMapper.map(cityDTO, City.class));
    }

}
