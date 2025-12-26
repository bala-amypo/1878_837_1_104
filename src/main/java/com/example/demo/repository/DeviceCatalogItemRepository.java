package com.example.demo.repository;

import com.example.demo.model.DeviceCatalogItem;
import java.util.*;

public interface DeviceCatalogItemRepository {
    Optional<DeviceCatalogItem> findByDeviceCode(String code);
    Optional<DeviceCatalogItem> findById(Long id);
    List<DeviceCatalogItem> findAll();
    DeviceCatalogItem save(DeviceCatalogItem item);
}
