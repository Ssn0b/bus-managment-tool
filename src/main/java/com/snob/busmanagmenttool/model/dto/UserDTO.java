package com.snob.busmanagmenttool.model.dto;

public record UserDTO(
        Long id,
        String firstname,
        String lastname,
        String email,
        double balance
) {}
