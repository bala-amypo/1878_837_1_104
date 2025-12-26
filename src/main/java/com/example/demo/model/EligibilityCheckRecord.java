package com.example.demo.model;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
public class EligibilityCheckRecord {
    private Long id;
    private Long employeeId;
    private Long deviceItemId;
    private Boolean isEligible;
    private String reason;
    private LocalDateTime checkedAt;

    public void prePersist() {
        this.checkedAt = LocalDateTime.now();
    }
}
