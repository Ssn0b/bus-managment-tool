package com.snob.busmanagmenttool.service.user;

import com.snob.busmanagmenttool.exception.EntityNotFoundException;
import com.snob.busmanagmenttool.model.dto.UserDTO;
import com.snob.busmanagmenttool.model.entity.user.User;
import com.snob.busmanagmenttool.repository.user.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    public List<UserDTO> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }
    public Optional<UserDTO> getUserById(UUID id){
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with ID " +
                id + " not found."));
        return Optional.ofNullable(modelMapper.map(user, UserDTO.class));
    }
}
