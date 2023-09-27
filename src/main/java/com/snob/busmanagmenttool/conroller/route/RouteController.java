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
@RequestMapping("/api/v1/routes")
@RequiredArgsConstructor
public class RouteController {
    private final RouteService routeService;
    @GetMapping
    public List<RouteDTO> getRoutes() {
        return routeService.getAllRoutes();
    }
    @GetMapping("/{id}")
    public Optional<RouteDTO> getRoute(@PathVariable Long id) throws EntityNotFoundException {
        return routeService.getRouteById(id);
    }
}
