package ru.samarina.CourseProject.service;

import ru.samarina.CourseProject.dto.StoreDto;

import java.util.List;

public interface StoreService {

    List<StoreDto> findAllStores();
    StoreDto findStoreById(Long id);
    void saveStore(StoreDto storeDto);
}
