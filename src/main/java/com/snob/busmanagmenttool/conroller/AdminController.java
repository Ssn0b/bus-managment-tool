package com.snob.busmanagmenttool.conroller;

import com.snob.busmanagmenttool.exception.EntityNotFoundException;
import com.snob.busmanagmenttool.model.dto.BusDTO;
import com.snob.busmanagmenttool.model.dto.UserDTO;
import com.snob.busmanagmenttool.service.BusService;
import com.snob.busmanagmenttool.service.UserService;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @PutMapping
    @PreAuthorize("hasAuthority('admin:update')")
    public String put() {
        return "PUT:: admin controller";
    }

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
    @PostMapping("/buses")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<String> addBus(@RequestBody BusDTO bus) {

        log.info("start adding bus...");
        busService.saveBus(bus);
        log.info("added");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Bus added");
    }
    @PatchMapping("/buses/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<String> updateBus(@PathVariable("id") Long id, @RequestBody Map<String, Object> updatedFields){

        log.info("start adding bus...");
        busService.updateBus(id,updatedFields);
        log.info("added");

        return ResponseEntity.status(HttpStatus.OK)
                .body("Bus updated");
    }
    @GetMapping("/buses")
    @PreAuthorize("hasAuthority('admin:read')")
    public List<BusDTO> getBuses() {
        return busService.getAllBuses();
    }
    @GetMapping("/buses/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public Optional<BusDTO> getBus(@PathVariable Long id) throws EntityNotFoundException {
        return busService.getBusById(id);
    }
    @DeleteMapping("/buses/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public void deleteBus(@PathVariable Long id) throws EntityNotFoundException {
        busService.deleteBusById(id);
    }
}
