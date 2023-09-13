package com.snob.busmanagmenttool.auth;

import com.snob.busmanagmenttool.model.entity.user.Role;
import com.snob.busmanagmenttool.model.entity.user.Specialization;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegisterRequest {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private double balance;
    private Role role;
    private int experience;
    private Specialization specialization;
}
