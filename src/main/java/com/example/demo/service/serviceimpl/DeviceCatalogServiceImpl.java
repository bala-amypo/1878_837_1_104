package com.example.demo.serviceimpl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.DeviceCatalogItem;
import com.example.demo.repository.DeviceCatalogItemRepository;
import com.example.demo.service.DeviceCatalogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceCatalogServiceImpl implements DeviceCatalogService {

    private final DeviceCatalogItemRepository repository;

    public DeviceCatalogServiceImpl(DeviceCatalogItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public DeviceCatalogItem createItem(DeviceCatalogItem item) {
        if (repository.findByDeviceCode(item.getDeviceCode()).isPresent()) {
            throw new BadRequestException("Device code already exists");
        }
        if (item.getMaxAllowedPerEmployee() <= 0) {
            throw new BadRequestException("maxAllowedPerEmployee must be greater than 0");
        }
        return repository.save(item);
    }

    @Override
    public List<DeviceCatalogItem> getAllItems() {
        return repository.findAll();
    }

    @Override
    public DeviceCatalogItem updateActiveStatus(Long id, boolean active) {
        DeviceCatalogItem item = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));
        item.setActive(active);
        return repository.save(item);
    }
}