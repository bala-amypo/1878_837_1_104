package com.example.demo.dto;
import java.time.LocalDate;
public record IssuedDeviceDto(Long employeeId, Long deviceItemId, LocalDate issuedDate,
                              LocalDate returnedDate, String status) {}