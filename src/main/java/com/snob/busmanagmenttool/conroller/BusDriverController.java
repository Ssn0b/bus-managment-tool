package com.snob.busmanagmenttool.conroller;

import com.snob.busmanagmenttool.model.dto.BusDTO;
import com.snob.busmanagmenttool.model.dto.BusDriverDTO;
import com.snob.busmanagmenttool.service.user.BusDriverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
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
}
