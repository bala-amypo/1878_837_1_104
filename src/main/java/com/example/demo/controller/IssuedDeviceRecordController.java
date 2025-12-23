package com.example.demo.controller;

import com.example.demo.model.IssuedDeviceRecord;
import com.example.demo.service.IssuedDeviceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issued-devices")
public class IssuedDeviceRecordController {

    @Autowired
    private IssuedDeviceRecordService service;

    @GetMapping("/count-active/{employeeId}")
    public int countActiveDevices(@PathVariable Long employeeId) {
        return service.countActiveDeviceForEmployee(employeeId);
    }

    @GetMapping("/employee/{employeeId}")
    public List<IssuedDeviceRecord> getDevices(@PathVariable Long employeeId) {
        return service.getDevicesByEmployeeId(employeeId);
    }

    @PostMapping
    public IssuedDeviceRecord issueDevice(@RequestBody IssuedDeviceRecord record) {
        return service.issueDevice(record);
    }

    @PutMapping("/deactivate/{id}")
    public void deactivateDevice(@PathVariable Long id) {
        service.deactivateDevice(id);
    }
}
