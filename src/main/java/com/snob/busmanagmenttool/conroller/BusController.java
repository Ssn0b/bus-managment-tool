package com.snob.busmanagmenttool.conroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snob.busmanagmenttool.exception.EntityNotFoundException;
import com.snob.busmanagmenttool.model.dto.BusDTO;
import com.snob.busmanagmenttool.model.entity.machinery.BusStatus;
import com.snob.busmanagmenttool.model.entity.user.User;
import com.snob.busmanagmenttool.service.BusService;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.snob.busmanagmenttool.service.aws.S3Service;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/v1/management/buses")
@PreAuthorize("hasRole('ADMIN') || hasRole('MANAGER')")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class BusController {
    private final BusService busService;
    private final S3Service s3Service;
    private final ObjectMapper objectMapper;
    @PostMapping
    @PreAuthorize("hasAuthority('admin:create') || hasAuthority('management:create')")
    public ResponseEntity<String> addBus(@RequestParam("file") MultipartFile file,
                                         @RequestParam("bus") String busData) {
        try {
            String photoUrl = s3Service.uploadBusImage(file);
            BusDTO busDTO = objectMapper.readValue(busData, BusDTO.class);
            busDTO.setPhotoUrl(photoUrl);
            busService.saveBus(busDTO);

            return ResponseEntity.ok("Bus added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding bus: " + e.getMessage());
        }
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
