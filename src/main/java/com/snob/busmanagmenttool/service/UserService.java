package com.snob.busmanagmenttool.service;

import com.snob.busmanagmenttool.model.dto.UserDTO;
import com.snob.busmanagmenttool.model.mapper.UserDTOMapper;
import com.snob.busmanagmenttool.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;
    public List<UserDTO> getAllUsers(){
    return userRepository.findAll()
            .stream()
            .map(userDTOMapper)
            .collect(Collectors.toList());
    }
}
