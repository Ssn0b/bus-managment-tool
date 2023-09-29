package com.snob.busmanagmenttool.conroller.route;

import com.snob.busmanagmenttool.model.dto.TicketDTO;
//import com.snob.busmanagmenttool.service.TicketService;
import com.snob.busmanagmenttool.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/management/tickets")
@PreAuthorize("hasRole('ADMIN') || hasRole('MANAGER')")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("/{id}")
    public ResponseEntity<TicketDTO> getTicketById(@PathVariable String id) {
        Optional<TicketDTO> ticketDTO = ticketService.getTicketById(id);
        return ticketDTO.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/trip/{tripId}")
    public List<TicketDTO> getAllTicketsByTripId(@PathVariable String tripId) {
        return ticketService.getAllTicketsByTripId(tripId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTicket(
            @PathVariable String id,
            @RequestBody Map<String, Object> updatedFields
    ) {
//        ticketService.updateTicket(id, updatedFields);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Ticket updated");
    }
}
