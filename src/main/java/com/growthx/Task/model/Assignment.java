package com.growthx.Task.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Document(collection = "assignments")
public class Assignment {

    @Id
    private String id;
    private String userId;
    private String task;
    private String adminId;
    private String status; // 'pending', 'accepted', or 'rejected'
    private LocalDateTime createdAt;

    public Assignment() {
        this.createdAt = LocalDateTime.now();
    }
}
