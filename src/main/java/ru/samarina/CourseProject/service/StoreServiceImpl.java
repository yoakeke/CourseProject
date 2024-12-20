package ru.samarina.CourseProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.samarina.CourseProject.dto.StoreDto;
import ru.samarina.CourseProject.entity.Store;
import ru.samarina.CourseProject.repository.StoreRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    @Autowired
    public StoreServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public List<StoreDto> getAllStores() {
        // Получаем все магазины
        List<Store> stores = storeRepository.findAll();
        // Преобразуем магазины в StoreDto
        return stores.stream()
                .map(store -> new StoreDto(store.getId(), store.getName(), null)) // Если вам нужно больше данных, можно добавлять
                .collect(Collectors.toList());
    }

    @Override
    public StoreDto addStore(StoreDto storeDto) {
        // Преобразуем StoreDto в сущность Store
        Store store = new Store();
        store.setName(storeDto.getName());

        // Сохраняем магазин в базе данных
        Store savedStore = storeRepository.save(store);

        // Возвращаем StoreDto с ID созданного магазина
        return new StoreDto(savedStore.getId(), savedStore.getName(), null);
    }

    @Override
    public StoreDto updateStore(Long id, StoreDto storeDto) {
        // Находим существующий магазин
        Store store = storeRepository.findById(id).orElseThrow(() -> new RuntimeException("Store not found"));

        // Обновляем данные
        store.setName(storeDto.getName());

        // Сохраняем обновленные данные
        Store updatedStore = storeRepository.save(store);

        // Возвращаем обновленные данные в DTO
        return new StoreDto(updatedStore.getId(), updatedStore.getName(), null);
    }

    @Override
    public void deleteStore(Long id) {
        // Проверяем существование магазина
        Store store = storeRepository.findById(id).orElseThrow(() -> new RuntimeException("Store not found"));
        // Удаляем магазин
        storeRepository.delete(store);
    }
}
