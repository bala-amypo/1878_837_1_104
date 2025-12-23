package com.example.demo.service.impl;

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
    public DeviceCatalogItem create(DeviceCatalogItem item) {
        return repository.save(item);
    }

    @Override
    public DeviceCatalogItem getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<DeviceCatalogItem> getAll() {
        return repository.findAll();
    }

    @Override
    public DeviceCatalogItem update(Long id, DeviceCatalogItem item) {
        item.setId(id);
        return repository.save(item);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
