package com.snob.busmanagmenttool.conroller.route;

import com.snob.busmanagmenttool.exception.EntityNotFoundException;
import com.snob.busmanagmenttool.model.dto.StopDTO;
import com.snob.busmanagmenttool.service.StopService;
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
@RequestMapping("/api/v1/management/stops")
@PreAuthorize("hasRole('ADMIN') || hasRole('MANAGER')")
@RequiredArgsConstructor
public class StopController {
    private final StopService stopService;
    @PostMapping
    @PreAuthorize("hasAuthority('admin:create') || hasAuthority('management:create')")
    public ResponseEntity<String> addStop(@RequestBody StopDTO stop) {

        log.info("start adding route...");
        stopService.saveStop(stop);
        log.info("added");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Route added");
    }
    @GetMapping
    @PreAuthorize("hasAuthority('admin:read') || hasAuthority('management:read')")
    public List<StopDTO> getStops() {
        return stopService.getAllStops();
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:read') || hasAuthority('management:read')")
    public Optional<StopDTO> getStop(@PathVariable Long id) throws EntityNotFoundException {
        return stopService.getStopById(id);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public void deleteStop(@PathVariable Long id) throws EntityNotFoundException {
        stopService.deleteStopById(id);
    }
}
