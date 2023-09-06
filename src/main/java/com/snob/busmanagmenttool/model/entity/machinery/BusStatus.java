package com.snob.busmanagmenttool.model.entity.machinery;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BusStatus {
    BROKEN,
    REPAIRING,
    ACTIVE,
    PRESERVED
}
