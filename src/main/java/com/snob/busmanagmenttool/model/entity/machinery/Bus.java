package com.snob.busmanagmenttool.model.entity.machinery;

import com.snob.busmanagmenttool.model.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "_bus")
public class Bus {
    @Id
    @GeneratedValue
    private Long id;
    private String brand;
    private int seats;
    private String carNumber;
    private boolean active;
    @OneToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private User driver;
}

