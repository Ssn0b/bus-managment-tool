package com.snob.busmanagmenttool.model.entity.machinery;

import com.snob.busmanagmenttool.model.entity.user.BusDriver;
import com.snob.busmanagmenttool.model.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "_bus")
public class Bus {
    @Id
    @GeneratedValue
    private UUID id;
    private String brand;
    private int seats;
    private String carNumber;
    @Enumerated(EnumType.STRING)
    private BusStatus busStatus;
    private String photoUrl;
    @OneToOne
    @JoinColumn(name = "driver_id")
    private User driver;
}

