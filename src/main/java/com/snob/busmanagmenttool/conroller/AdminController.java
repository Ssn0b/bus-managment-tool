package com.snob.busmanagmenttool.conroller;

import com.snob.busmanagmenttool.exception.EntityNotFoundException;
import com.snob.busmanagmenttool.model.dto.BusDTO;
import com.snob.busmanagmenttool.model.dto.CityDTO;
import com.snob.busmanagmenttool.model.dto.UserDTO;
import com.snob.busmanagmenttool.model.entity.machinery.Bus;
import com.snob.busmanagmenttool.service.BusService;
import com.snob.busmanagmenttool.service.CityService;
import com.snob.busmanagmenttool.service.TicketService;
import com.snob.busmanagmenttool.service.aws.S3Service;
import com.snob.busmanagmenttool.service.user.BusDriverService;
import com.snob.busmanagmenttool.service.user.UserService;

import java.io.IOException;
import java.util.*;

import com.snob.busmanagmenttool.service.user.actions.FeedbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final BusService busService;
    private final CityService cityService;
    private final BusDriverService busDriverService;
    private final FeedbackService feedbackService;
    private final TicketService ticketService;

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('admin:read')")
    public List<UserDTO> getUsers() {
        return userService.getAllUsers();
    }
    @GetMapping("/users/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public Optional<UserDTO> getUser(@PathVariable UUID id) throws EntityNotFoundException{
        return userService.getUserById(id);
    }

  @DeleteMapping("/buses/{id}")
  @PreAuthorize("hasAuthority('admin:delete')")
  public ResponseEntity<String> deleteBus(@PathVariable UUID id) throws EntityNotFoundException {
        busService.deleteBusById(id);
        return ResponseEntity.ok("Bus deleted successfully.");
    }

    @PostMapping("/city")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<String> addCity(@RequestBody CityDTO cityDTO) {
        cityService.saveCity(cityDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("City added");
    }
    @DeleteMapping("/driver/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public void deleteBusDriver(@PathVariable UUID id) throws EntityNotFoundException {
        busDriverService.deleteBusDriverById(id);
    }
    @DeleteMapping("/feedback/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<Void> deleteFeedback(@PathVariable String id) {
        feedbackService.deleteFeedback(id);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/ticket/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<Void> deleteTicket(@PathVariable String id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.ok().build();
    }
}
