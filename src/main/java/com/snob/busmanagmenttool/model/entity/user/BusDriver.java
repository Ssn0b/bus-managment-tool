package com.snob.busmanagmenttool.model.entity.user;

import com.snob.busmanagmenttool.model.entity.machinery.Bus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@Entity
public class BusDriver extends User{
    private int workExperience;
}
