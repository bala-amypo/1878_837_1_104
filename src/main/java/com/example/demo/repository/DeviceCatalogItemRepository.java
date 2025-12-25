package com.example.demo.repository;

import com.example.demo.model.DeviceCatalogItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface DeviceCatalogItemRepository extends JpaRepository<DeviceCatalogItem, Long> {
    Optional<DeviceCatalogItem> findByDeviceCode(String deviceCode);
    List<DeviceCatalogItem> findAll();
}