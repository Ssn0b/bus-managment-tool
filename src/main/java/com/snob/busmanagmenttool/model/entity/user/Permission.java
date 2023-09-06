package com.snob.busmanagmenttool.model.entity.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    MANAGER_READ("management:read"),
    MANAGER_UPDATE("management:update"),
    MANAGER_CREATE("management:create"),
    MANAGER_DELETE("management:delete"),
    DRIVER_READ("driver:read"),
    DRIVER_UPDATE("driver:update"),
    REPAIRMAN_UPDATE("repairman:update"),
    REPAIRMAN_READ("repairman:read");
    @Getter
    private final String permission;
}