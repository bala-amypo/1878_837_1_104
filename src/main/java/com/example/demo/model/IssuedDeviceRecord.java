package com.example.demo.model;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter
public class IssuedDeviceRecord {
    private Long id;
    private Long employeeId;
    private Long deviceItemId;
    private String status; // ISSUED / RETURNED
    private LocalDate returnedDate;
}
