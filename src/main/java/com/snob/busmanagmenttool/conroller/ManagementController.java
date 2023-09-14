package com.snob.busmanagmenttool.conroller;

import com.snob.busmanagmenttool.exception.EntityNotFoundException;
import com.snob.busmanagmenttool.model.dto.RouteDTO;
import com.snob.busmanagmenttool.service.BusService;
import com.snob.busmanagmenttool.service.RouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/management")
@PreAuthorize("hasRole('ADMIN') || hasRole('MANAGER')")
@RequiredArgsConstructor
public class ManagementController {
    private final BusService busService;
    private final RouteService routeService;

    @PostMapping("/routes")
    @PreAuthorize("hasAuthority('admin:create') || hasAuthority('management:create')")
    public ResponseEntity<String> addRoute(@RequestBody RouteDTO route) {

        log.info("start adding route...");
        routeService.saveRoute(route);
        log.info("added");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Route added");
    }
    @DeleteMapping("/routes/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public void deleteRoute(@PathVariable Long id) throws EntityNotFoundException {
        routeService.deleteRouteById(id);
    }

}