package com.snob.busmanagmenttool.model.dto;

import com.snob.busmanagmenttool.model.entity.machinery.Bus;
import com.snob.busmanagmenttool.model.entity.machinery.BusParts;
import com.snob.busmanagmenttool.model.entity.user.Repairman;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RepairDocumentDTO {
    private String id;
    private String repairDocNum;
    private UUID busId;
    private Timestamp startDateOfRepair;
    private Timestamp endDateOfRepair;
    private List<BusParts> busPartsForRepair;
    private String description;
    private UUID repairmanId;
    private Double price;
}
