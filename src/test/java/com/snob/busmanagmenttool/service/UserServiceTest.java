package com.snob.busmanagmenttool.service;

import com.snob.busmanagmenttool.model.dto.UserDTO;
import com.snob.busmanagmenttool.model.entity.user.Role;
import com.snob.busmanagmenttool.model.entity.user.User;
import com.snob.busmanagmenttool.repository.user.UserRepository;
import com.snob.busmanagmenttool.service.user.UserService;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserService userService;

//    @Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void testGetAllUsers() {
//        // Create some sample user data for testing
//        List<User> users = new ArrayList<>();
//        User user1 = User.builder()
//                .firstname("User1")
//                .lastname("User1")
//                .email("User1@mail.com")
//                .password("password")
//                .username("User1")
//                .role(Role.USER)
//                .build();
//        User user2 = User.builder()
//                .firstname("User2")
//                .lastname("User2")
//                .email("User2@mail.com")
//                .password("password")
//                .username("User2")
//                .role(Role.USER)
//                .build();
//        users.add(user1);
//        users.add(user2);
//
//        // Create corresponding DTOs for the sample users
//        List<UserDTO> userDTOs = new ArrayList<>();
//        UserDTO userDTO1 = UserDTO.builder()
//                .firstname("UserDTO1")
//                .lastname("UserDTO1")
//                .email("UserDTO1@mail.com")
//                .role(Role.USER)
//                .build();
//        UserDTO userDTO2 = UserDTO.builder()
//                .firstname("UserDTO2")
//                .lastname("UserDTO2")
//                .email("UserDTO2@mail.com")
//                .role(Role.USER)
//                .build();
//        userDTOs.add(userDTO1);
//        userDTOs.add(userDTO2);
//
//        // Mock the UserRepository to return the sample users when findAll is called
//        when(userRepository.findAll()).thenReturn(users);
//
//        // Mock the ModelMapper to return the corresponding DTOs when map is called
//        when(modelMapper.map(user1, UserDTO.class)).thenReturn(userDTO1);
//        when(modelMapper.map(user2, UserDTO.class)).thenReturn(userDTO2);
//
//        // Call the method under test
//        List<UserDTO> result = userService.getAllUsers();
//
//        // Verify the result
//        assertEquals(userDTOs.size(), result.size());
//        assertEquals(userDTOs.get(0), result.get(0));
//        assertEquals(userDTOs.get(1), result.get(1));
//    }
}