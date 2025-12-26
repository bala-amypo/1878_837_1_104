package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.DeviceCatalogItem;
import com.example.demo.repository.DeviceCatalogItemRepository;
import com.example.demo.service.DeviceCatalogService;

import java.util.List;

public class DeviceCatalogServiceImpl implements DeviceCatalogService {

    private final DeviceCatalogItemRepository repo;

    public DeviceCatalogServiceImpl(DeviceCatalogItemRepository repo) {
        this.repo = repo;
    }

    public DeviceCatalogItem createItem(DeviceCatalogItem item) {
        if (item.getMaxAllowedPerEmployee() == null || item.getMaxAllowedPerEmployee() <= 0)
            throw new BadRequestException("maxAllowedPerEmployee");
        return repo.save(item);
    }

    public DeviceCatalogItem updateActiveStatus(Long id, boolean active) {
        DeviceCatalogItem item = repo.findById(id).orElseThrow();
        item.setActive(active);
        return repo.save(item);
    }

    public List<DeviceCatalogItem> getAllItems() {
        return repo.findAll();
    }
}
