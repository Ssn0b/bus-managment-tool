//package com.snob.busmanagmenttool.service;
//
//import com.snob.busmanagmenttool.exception.DriverAlreadyHasBusException;
//import com.snob.busmanagmenttool.model.dto.BusDTO;
//import com.snob.busmanagmenttool.model.entity.machinery.Bus;
//import com.snob.busmanagmenttool.model.entity.user.Role;
//import com.snob.busmanagmenttool.model.entity.user.User;
//import com.snob.busmanagmenttool.repository.BusRepository;
//import com.snob.busmanagmenttool.repository.user.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.modelmapper.ModelMapper;
//import org.modelmapper.TypeMap;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//
//public class BusServiceTest {
//    @Mock
//    private BusRepository busRepository;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private ModelMapper modelMapper;
//    @Mock
//    TypeMap<Bus, BusDTO> typeMap;
//    @InjectMocks
//    private BusService busService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//    @Test
//    public void testGetAllBuses() {
//        when(busRepository.findAll()).thenReturn(List.of(new Bus()));
//        when(modelMapper.createTypeMap(Bus.class, BusDTO.class)).thenReturn(typeMap);
//
//        List<BusDTO> result = busService.getAllBuses();
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//    }
//
//    @Test
//    public void testGetBusById() {
//        Bus sampleBus = new Bus();
//        sampleBus.setId(1L);
//        sampleBus.setBrand("BMW");
//
//        BusDTO sampleBusDTO = new BusDTO();
//        sampleBusDTO.setBrand("BMW");
//
//        // Mock behavior of busRepository and modelMapper
//        when(busRepository.findById(1L)).thenReturn(Optional.of(sampleBus));
//        when(modelMapper.map(sampleBus, BusDTO.class)).thenReturn(sampleBusDTO);
//
//        Optional<BusDTO> result = busService.getBusById(1L);
//
//        assertTrue(result.isPresent());
//        assertEquals("BMW", result.get().getBrand());
//    }
//
//    @Test
//    public void testSaveBus() {
//        // Create a sample BusDTO
//        BusDTO busDTO = new BusDTO();
//        busDTO.setDriverId(1L);
//
//        // Create a sample User
//        User driver = new User();
//        driver.setId(1L);
//        driver.setRole(Role.DRIVER);
//
//        // Mock behavior of userRepository and busRepository
//        when(userRepository.findById(1L)).thenReturn(Optional.of(driver));
//        when(busRepository.existsBusByDriverId(1L)).thenReturn(false);    //@TODO test all scenarios
//
//        // Call the method under test
////        assertDoesNotThrow(() -> busService.saveBus(busDTO));
//    }
//
//    @Test
//    public void testSaveBusDriverAlreadyHasBus() {
//        // Create a sample BusDTO
//        BusDTO busDTO = new BusDTO();
//        busDTO.setDriverId(1L);
//
//        // Create a sample User
//        User driver = new User();
//        driver.setId(1L);
//        driver.setRole(Role.DRIVER);
//
//        // Mock behavior of userRepository and busRepository
//        when(userRepository.findById(1L)).thenReturn(Optional.of(driver));
//        when(busRepository.existsBusByDriverId(1L)).thenReturn(true);
//
//        // Call the method under test and expect an exception
////        assertThrows(DriverAlreadyHasBusException.class, () -> busService.saveBus(busDTO));
//    }
//
//}
