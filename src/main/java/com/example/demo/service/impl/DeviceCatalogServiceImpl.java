package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.DeviceCatalogItem;
import com.example.demo.repository.DeviceCatalogItemRepository;
import com.example.demo.service.DeviceCatalogService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DeviceCatalogServiceImpl implements DeviceCatalogService {

    private final DeviceCatalogItemRepository deviceRepo;

    // Requirement: Constructor injection
    public DeviceCatalogServiceImpl(DeviceCatalogItemRepository deviceRepo) {
        this.deviceRepo = deviceRepo;
    }

    @Override
    public DeviceCatalogItem createItem(DeviceCatalogItem item) {
        // 1. Validate maxAllowedPerEmployee > 0 (Fixes the failed test)
        if (item.getMaxAllowedPerEmployee() == null || item.getMaxAllowedPerEmployee() <= 0) {
            throw new BadRequestException("Invalid maxAllowedPerEmployee: must be greater than 0");
        }

        // 2. Validate unique deviceCode
        Optional<DeviceCatalogItem> existing = deviceRepo.findByDeviceCode(item.getDeviceCode());
        if (existing.isPresent()) {
            throw new BadRequestException("Device code already exists");
        }

        return deviceRepo.save(item);
    }

    @Override
    public List<DeviceCatalogItem> getAllItems() {
        return deviceRepo.findAll();
    }

    @Override
    public DeviceCatalogItem updateActiveStatus(Long id, boolean active) {
        DeviceCatalogItem item = deviceRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Device not found"));
        item.setActive(active);
        return deviceRepo.save(item);
    }
}