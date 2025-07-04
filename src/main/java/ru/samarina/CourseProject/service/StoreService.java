package ru.samarina.CourseProject.service;

import ru.samarina.CourseProject.entity.Store;
import java.util.List;

public interface StoreService {
    List<Store> getAllStores();
    void addStore(Store store);
    void deleteStore(Long id);
    void updateStore(Store store);
}
