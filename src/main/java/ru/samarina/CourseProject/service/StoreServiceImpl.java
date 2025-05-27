package ru.samarina.CourseProject.service;

import org.springframework.stereotype.Service;
import ru.samarina.CourseProject.entity.Store;
import ru.samarina.CourseProject.repository.StoreRepository;
import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    public StoreServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    // Находим все магазины
    @Override
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    // Добавляем новый магазин
    @Override
    public void addStore(Store store) {
        storeRepository.save(store);
    }

    // Удаляем магазин
    @Override
    public void deleteStore(Long id) {
        storeRepository.deleteById(id);
    }

    // Обновляем магазин
    @Override
    public void updateStore(Store store) {
        storeRepository.save(store);
    }

}
