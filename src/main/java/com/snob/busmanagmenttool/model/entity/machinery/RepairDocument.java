package com.snob.busmanagmenttool.model.entity.machinery;

import com.snob.busmanagmenttool.model.entity.user.Repairman;
import com.snob.busmanagmenttool.model.entity.user.User;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "repair")
public class RepairDocument {
    @Id
    private String id;
    private String repairDocNum;
    @ManyToOne
    @JoinColumn(name = "bus_id")
    private Bus bus;
    private Timestamp startDateOfRepair;
    private Timestamp endDateOfRepair;
    private List<BusParts> busPartsForRepair;
    private String description;
    @OneToOne
    @JoinColumn(name = "repairman_id", nullable = false)
    private Repairman repairman;
}
