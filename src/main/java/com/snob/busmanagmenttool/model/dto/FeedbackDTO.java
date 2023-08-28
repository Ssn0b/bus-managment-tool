package com.snob.busmanagmenttool.model.dto;

import com.snob.busmanagmenttool.model.entity.user.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FeedbackDTO {
    private String id;
    private String title;
    private int rating;
    private String comment;
    private Timestamp timestamp;
    private Long userId;
}
