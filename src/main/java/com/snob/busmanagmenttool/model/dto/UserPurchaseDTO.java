package com.snob.busmanagmenttool.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserPurchaseDTO {
    private UUID userId;
    private UUID tripId;
    private List<Integer> seatNumbers;
}
