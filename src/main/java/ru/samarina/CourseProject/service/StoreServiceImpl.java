package ru.samarina.CourseProject.service;

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
    public List<StoreDto> findAllStores() {
        return storeRepository.findAll().stream().map(store -> {
            StoreDto storeDto = new StoreDto();
            storeDto.setId(store.getId());
            storeDto.setName(store.getName());
            storeDto.setAddress(store.getLocation());
            return storeDto;
        }).collect(Collectors.toList());
    }

    @Override
    public StoreDto findStoreById(Long id) {
        Store store = storeRepository.findById(id).orElseThrow(() -> new RuntimeException("Store not found"));
        StoreDto storeDto = new StoreDto();
        storeDto.setId(store.getId());
        storeDto.setName(store.getName());
        storeDto.setAddress(store.getLocation());
        return storeDto;
    }

    @Override
    public void saveStore(StoreDto storeDto) {
        Store store = new Store();
        store.setName(storeDto.getName());
        store.setLocation(storeDto.getAddress());
        storeRepository.save(store);
    }
}
