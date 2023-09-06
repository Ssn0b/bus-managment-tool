package com.snob.busmanagmenttool.model.dto;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.snob.busmanagmenttool.model.entity.machinery.BusStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BusDTO{
    private String brand;
    private int seats;
    private String carNumber;
    private Long driverId;
    private BusStatus busStatus;
    private String photoUrl;

}
