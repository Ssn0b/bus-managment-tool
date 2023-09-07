package com.snob.busmanagmenttool.conroller.route;

import com.snob.busmanagmenttool.model.dto.FeedbackDTO;
import com.snob.busmanagmenttool.model.dto.TicketDTO;
//import com.snob.busmanagmenttool.service.TicketService;
import com.snob.busmanagmenttool.service.TicketService;
import com.snob.busmanagmenttool.service.user.actions.FeedbackService;
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
@RequestMapping("/api/v1/management/ticket")
@PreAuthorize("hasRole('ADMIN') || hasRole('MANAGER')")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    @GetMapping
    public ResponseEntity<List<TicketDTO>> getAllTickets() {
        List<TicketDTO> allTickets = ticketService.getAllTickets();
        return ResponseEntity.ok(allTickets);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TicketDTO> getTicketById(@PathVariable String id) {
        Optional<TicketDTO> ticketDTO = ticketService.getTicketById(id);
        return ticketDTO.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> createTicket(@RequestBody TicketDTO ticketDTO) {
        ticketService.createTicket(ticketDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTicket(
            @PathVariable String id,
            @RequestBody Map<String, Object> updatedFields
    ) {
        ticketService.updateTicket(id, updatedFields);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Ticket updated");
    }
}
