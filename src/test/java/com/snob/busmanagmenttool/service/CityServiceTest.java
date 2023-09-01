package com.snob.busmanagmenttool.service;

import com.snob.busmanagmenttool.model.dto.CityDTO;
import com.snob.busmanagmenttool.model.entity.route.City;
import com.snob.busmanagmenttool.repository.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class CityServiceTest {
    @Mock
    private CityRepository cityRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CityService cityService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveCity() {
        // Create a sample CityDTO
        CityDTO cityDTO = new CityDTO();
        cityDTO.setName("Sample City");

        // Create a sample City entity
        City cityEntity = new City();
        cityEntity.setName("Sample City");

        // Mock the behavior of modelMapper
        when(modelMapper.map(cityDTO, City.class)).thenReturn(cityEntity);

        // Call the method under test
        cityService.saveCity(cityDTO);

        // Verify that cityRepository.save was called with the mapped entity
        verify(cityRepository).save(cityEntity);
    }
}
