package com.snob.busmanagmenttool.conroller.route;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.snob.busmanagmenttool.exception.EntityNotFoundException;
import com.snob.busmanagmenttool.model.dto.TripDTO;
import com.snob.busmanagmenttool.service.TripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/management/trips")
@PreAuthorize("hasRole('ADMIN') || hasRole('MANAGER')")
@RequiredArgsConstructor
public class TripController {
    private final TripService tripService;
    @PostMapping
    @PreAuthorize("hasAuthority('admin:create') || hasAuthority('management:create')")
    public ResponseEntity<String> addTrip(@RequestBody TripDTO trip) {
        log.info("start adding trip...");
        tripService.saveTrip(trip);
        log.info("added");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Trip added");
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:read') || hasAuthority('management:read')")
    public Optional<TripDTO> getTripById(@PathVariable UUID id) throws EntityNotFoundException {
        return tripService.getTripById(id);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public void deleteTrip(@PathVariable UUID id) throws EntityNotFoundException {
        tripService.deleteTripById(id);
    }
}
