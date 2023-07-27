package com.snob.busmanagmenttool.model.entity.route;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "_city")
public class City {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private double latitude;
    private double longitude;
}
