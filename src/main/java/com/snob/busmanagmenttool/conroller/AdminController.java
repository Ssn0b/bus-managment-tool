package com.snob.busmanagmenttool.conroller;

import com.snob.busmanagmenttool.exception.EntityNotFoundException;
import com.snob.busmanagmenttool.model.dto.UserDTO;
import com.snob.busmanagmenttool.service.BusService;
import com.snob.busmanagmenttool.service.UserService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final BusService busService;
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('admin:read')")
    public List<UserDTO> getUsers() {
        return userService.getAllUsers();
    }
    @GetMapping("/users/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public Optional<UserDTO> getUser(@PathVariable Long id) throws EntityNotFoundException{
        return userService.getUserById(id);
    }
    @DeleteMapping("/buses/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public void deleteBus(@PathVariable Long id) throws EntityNotFoundException {
        busService.deleteBusById(id);
    }
}
