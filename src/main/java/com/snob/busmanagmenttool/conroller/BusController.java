package com.snob.busmanagmenttool.conroller;

import com.snob.busmanagmenttool.exception.EntityNotFoundException;
import com.snob.busmanagmenttool.model.dto.BusDTO;
import com.snob.busmanagmenttool.service.BusService;
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
@RequestMapping("/api/v1/management/buses")
@PreAuthorize("hasRole('ADMIN') || hasRole('MANAGER')")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class BusController {
    private final BusService busService;
    @PostMapping
    @PreAuthorize("hasAuthority('admin:create') || hasAuthority('management:create')")
    public ResponseEntity<String> addBus(@RequestBody BusDTO bus) {

        log.info("start adding bus...");
        busService.saveBus(bus);
        log.info("added");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Bus added");
    }
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update') || hasAuthority('management:update')")
    public ResponseEntity<String> updateBus(@PathVariable("id") Long id, @RequestBody Map<String, Object> updatedFields){

        log.info("start adding bus...");
        busService.updateBus(id,updatedFields);
        log.info("added");

        return ResponseEntity.status(HttpStatus.OK)
                .body("Bus updated");
    }
    @GetMapping
    @PreAuthorize("hasAuthority('admin:read') || hasAuthority('management:read')")
    public List<BusDTO> getBuses() {
        return busService.getAllBuses();
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:read') || hasAuthority('management:read')")
    public Optional<BusDTO> getBus(@PathVariable Long id) throws EntityNotFoundException {
        return busService.getBusById(id);
    }

}
