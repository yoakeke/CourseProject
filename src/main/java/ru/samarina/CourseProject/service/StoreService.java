package ru.samarina.CourseProject.service;

import ru.samarina.CourseProject.dto.StoreDto;

import java.util.List;

public interface StoreService {
    List<StoreDto> getAllStores();
    StoreDto addStore(StoreDto storeDto);
    void deleteStore(Long storeId);
    StoreDto updateStore(Long id, StoreDto storeDto);
}
