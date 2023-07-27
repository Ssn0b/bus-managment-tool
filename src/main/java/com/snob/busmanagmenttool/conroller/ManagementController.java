package com.snob.busmanagmenttool.conroller;

import com.snob.busmanagmenttool.service.BusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/management")
@PreAuthorize("hasRole('ADMIN') || hasRole('MANAGER')")
@RequiredArgsConstructor
public class ManagementController {
    private final BusService busService;

}