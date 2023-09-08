package com.snob.busmanagmenttool.model.dto;

import com.snob.busmanagmenttool.model.entity.user.Role;
import com.snob.busmanagmenttool.model.entity.user.Specialization;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RepairmanDTO {
    private UUID id;
    private String firstname;
    private String lastname;
    private String email;
    private Role role;
    private double balance;
    private int workExperience;
    private Specialization specialization;
}
