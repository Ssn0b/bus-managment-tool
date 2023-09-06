package com.snob.busmanagmenttool.model.entity.user;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Specialization {
    ENGINE_REPAIR("Engine Repair"),
    BODYWORK("Bodywork"),
    ELECTRICAL_SYSTEMS("Electrical Systems"),
    TRANSMISSION("Transmission"),
    BRAKES("Brakes"),
    SUSPENSION("Suspension"),
    AIR_CONDITIONING("Air Conditioning"),
    PAINTING("Painting"),
    INTERIOR("Interior"),
    OTHER("Other");
    private final String displayName;
    @Override
    public String toString() {
        return displayName;
    }
}
