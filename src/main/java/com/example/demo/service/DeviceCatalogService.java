package com.example.demo.service;

import com.example.demo.model.DeviceCatalogItem;
import java.util.List;

public interface DeviceCatalogService {

    DeviceCatalogItem create(DeviceCatalogItem item);

    DeviceCatalogItem getById(Long id);

    List<DeviceCatalogItem> getAll();

    DeviceCatalogItem update(Long id, DeviceCatalogItem item);

    void delete(Long id);
}
