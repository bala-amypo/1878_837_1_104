package com.example.demo.dto;
import java.time.LocalDateTime;
public record EligibilityCheckDto(Long employeeId, Long deviceItemId, Boolean isEligible,
                                  String reason, LocalDateTime checkedAt) {}