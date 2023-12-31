package com.snob.busmanagmenttool.conroller;

import com.snob.busmanagmenttool.model.dto.BusDTO;
import com.snob.busmanagmenttool.model.dto.BusDriverDTO;
import com.snob.busmanagmenttool.service.user.BusDriverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/driver")
@PreAuthorize("hasRole('DRIVER') || hasRole('MANAGER') || hasRole('DRIVER')")
@RequiredArgsConstructor
public class BusDriverController {
    private final BusDriverService busDriverService;

    @GetMapping("/bus")
    @PreAuthorize("hasAuthority('admin:read') || hasAuthority('management:read') || hasAuthority('driver:read')")
    public Optional<BusDTO> getCurrentBusForDriver(Principal principal) {
        Optional<BusDriverDTO> busDriver = busDriverService.getBusDriverByUsername(principal.getName());
        return busDriverService.getCurrentBusById(busDriver.get().getId());
    }
    @PutMapping("/bus")
    public ResponseEntity<String> updateDriverInfo(Principal principal, @RequestBody Map<String, Object> updatedFields) {
        busDriverService.updateBusDriver(principal.getName(), updatedFields);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Bus updated");
    }
}
