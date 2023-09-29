package com.snob.busmanagmenttool.conroller;

import com.snob.busmanagmenttool.exception.EntityNotFoundException;
import com.snob.busmanagmenttool.model.dto.CityDTO;
import com.snob.busmanagmenttool.model.dto.TicketDTO;
import com.snob.busmanagmenttool.model.dto.TripDTO;
import com.snob.busmanagmenttool.model.dto.UserPurchaseDTO;
import com.snob.busmanagmenttool.service.CityService;
import com.snob.busmanagmenttool.service.TicketService;
import com.snob.busmanagmenttool.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@PreAuthorize("hasRole('ADMIN') || hasRole('MANAGER') || hasRole('USER')")
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final TripService tripService;
    private final CityService cityService;
    private final TicketService ticketService;

    @GetMapping("/trips")
      public List<TripDTO> getTrips() {
          return tripService.getAllTrips();
      }
    @GetMapping("/trips/{tripNum}")
    public Optional<TripDTO> getTripByTripNumber(@PathVariable String tripNum)
            throws EntityNotFoundException {
        return tripService.findByTripNumber(tripNum);
    }

    @GetMapping("/city/{id}")
    public Optional<CityDTO> getCityById(@PathVariable Long id) throws
            EntityNotFoundException{
        return cityService.findCityById(id);
    }

    @GetMapping("/tickets")
    public ResponseEntity<List<TicketDTO>> getAllTickets() {
        List<TicketDTO> allTickets = ticketService.getAllTickets();
        return ResponseEntity.ok(allTickets);
    }
    @PostMapping("/purchase/tickets")
    public ResponseEntity<String> purchaseTickets(@RequestBody UserPurchaseDTO userPurchaseDTO){
    System.out.println(userPurchaseDTO);
        ticketService.purchaseTickets(userPurchaseDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Tickets Purchased");
    }
}
