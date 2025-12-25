package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.DeviceCatalogItem;
import com.example.demo.repository.DeviceCatalogItemRepository;
import com.example.demo.service.DeviceCatalogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceCatalogServiceImpl implements DeviceCatalogService {

    private final DeviceCatalogItemRepository deviceCatalogItemRepository;

    public DeviceCatalogServiceImpl(DeviceCatalogItemRepository deviceCatalogItemRepository) {
        this.deviceCatalogItemRepository = deviceCatalogItemRepository;
    }

    @Override
    public DeviceCatalogItem createItem(DeviceCatalogItem item) {
        // Check duplicate deviceCode
        if (deviceCatalogItemRepository.findByDeviceCode(item.getDeviceCode()).isPresent()) {
            throw new BadRequestException("Device code already exists: " + item.getDeviceCode());
        }

        // Validate maxAllowedPerEmployee > 0
        if (item.getMaxAllowedPerEmployee() == null || item.getMaxAllowedPerEmployee() <= 0) {
            throw new BadRequestException("Invalid maxAllowedPerEmployee: must be greater than 0");
        }

        return deviceCatalogItemRepository.save(item);
    }

    @Override
    public DeviceCatalogItem updateActiveStatus(Long id, boolean active) {
        DeviceCatalogItem item = getItemById(id);
        item.setActive(active);
        return deviceCatalogItemRepository.save(item);
    }

    @Override
    public List<DeviceCatalogItem> getAllItems() {
        return deviceCatalogItemRepository.findAll();
    }

    @Override
    public DeviceCatalogItem getItemById(Long id) {
        return deviceCatalogItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found with id: " + id));
    }

    @Override
    public void deleteItem(Long id) {
        DeviceCatalogItem item = getItemById(id);
        deviceCatalogItemRepository.delete(item);
    }
}
