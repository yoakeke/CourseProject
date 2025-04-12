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

    public StoreServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    @Override
    public void addStore(Store store) {
        storeRepository.save(store);
    }

    @Override
    public void deleteStore(Long id) {
        storeRepository.deleteById(id);
    }

    @Override
    public Store getStoreById(Long id) {
        return storeRepository.findById(id).orElse(null);
    }

    @Override
    public void updateStore(Store store) {
        storeRepository.save(store);
    }

    @Override
    public List<Store> searchStores(String keyword) {
        return storeRepository.findByNameContainingIgnoreCase(keyword);
    }
}
