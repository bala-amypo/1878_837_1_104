// File: src/main/java/com/example/demo/service/IssuedDeviceRecordService.java
package com.example.demo.service;

import com.example.demo.model.IssuedDeviceRecord;
import java.util.List;

public interface IssuedDeviceRecordService {

    IssuedDeviceRecord issueDevice(IssuedDeviceRecord record);

    IssuedDeviceRecord returnDevice(Long recordId);

    List<IssuedDeviceRecord> getIssuedDevicesByEmployee(Long employeeId);
}