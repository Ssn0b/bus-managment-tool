package com.snob.busmanagmenttool.model.dto;

import com.snob.busmanagmenttool.model.entity.Role;

public record UserDTO(
        Long id,
        String firstname,
        String lastname,
        String email,
        Role role,
        double balance
) {}
