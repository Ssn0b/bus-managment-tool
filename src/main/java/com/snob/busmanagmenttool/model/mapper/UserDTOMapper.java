package com.snob.busmanagmenttool.model.mapper;

import com.snob.busmanagmenttool.model.dto.UserDTO;
import com.snob.busmanagmenttool.model.entity.User;
import java.util.function.Function;
import org.springframework.stereotype.Service;

@Service
public class UserDTOMapper implements Function<User, UserDTO> {
    @Override
    public UserDTO apply(User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getRole(),
                user.getBalance());
    }
}
