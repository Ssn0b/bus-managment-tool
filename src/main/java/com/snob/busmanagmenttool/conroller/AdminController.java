package com.snob.busmanagmenttool.conroller;

import com.snob.busmanagmenttool.model.dto.BusDTO;
import com.snob.busmanagmenttool.model.dto.UserDTO;
import com.snob.busmanagmenttool.model.entity.Bus;
import com.snob.busmanagmenttool.service.BusService;
import com.snob.busmanagmenttool.service.UserService;
import java.util.List;
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
    @DeleteMapping
    @PreAuthorize("hasAuthority('admin:delete')")
    public String delete() {
        return "DELETE:: admin controller";
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('admin:read')")
    public List<UserDTO> getUsers() {
        return userService.getAllUsers();
    }
    @PostMapping("/bus")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<String> addBus(@RequestBody Bus bus) {

        log.info("start adding bus...");
        busService.saveBus(bus);
        log.info("added");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Bus added");
    }
    @GetMapping("/buses")
    @PreAuthorize("hasAuthority('admin:read')")
    public List<BusDTO> getBuses() {
        return busService.getAllBuses();
    }
    @GetMapping("/bus/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public BusDTO getBus(@PathVariable Long id){
        return busService.getBusById(id);
    }
}
