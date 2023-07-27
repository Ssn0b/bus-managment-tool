package com.snob.busmanagmenttool.conroller.route;

import com.snob.busmanagmenttool.exception.EntityNotFoundException;
import com.snob.busmanagmenttool.model.dto.RouteDTO;
import com.snob.busmanagmenttool.service.RouteService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/management/routes")
@PreAuthorize("hasRole('ADMIN') || hasRole('MANAGER')")
@RequiredArgsConstructor
public class RouteController {
    private final RouteService routeService;
    @PostMapping
    @PreAuthorize("hasAuthority('admin:create') || hasAuthority('management:create')")
    public ResponseEntity<String> addRoute(@RequestBody RouteDTO route) {

        log.info("start adding route...");
        routeService.saveRoute(route);
        log.info("added");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Route added");
    }
    @GetMapping
    @PreAuthorize("hasAuthority('admin:read') || hasAuthority('management:read')")
    public List<RouteDTO> getRoutes() {
        return routeService.getAllRoutes();
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:read') || hasAuthority('management:read')")
    public Optional<RouteDTO> getRoute(@PathVariable Long id) throws EntityNotFoundException {
        return routeService.getRouteById(id);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public void deleteRoute(@PathVariable Long id) throws EntityNotFoundException {
        routeService.deleteRouteById(id);
    }
}
