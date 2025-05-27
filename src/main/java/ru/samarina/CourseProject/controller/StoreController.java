package ru.samarina.CourseProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.samarina.CourseProject.entity.Store;
import ru.samarina.CourseProject.service.StoreService;
import java.util.List;

@Controller
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    // Находим все магазины
    @GetMapping
    public String listStores(Model model) {
        List<Store> stores = storeService.getAllStores();
        model.addAttribute("stores", stores);
        return "stores";
    }

    // Добавляем новый магазин
    @PostMapping("/addStore")
    public String addStore(@RequestParam String name) {
        storeService.addStore(Store.builder().name(name).build());
        return "redirect:/stores";
    }

    // Удаляем магазин
    @GetMapping("/delete/{id}")
    public String deleteStore(@PathVariable Long id) {
        storeService.deleteStore(id);
        return "redirect:/stores";
    }

    // Обновляем магазин
    @PostMapping("/updateStore")
    public String updateStore(@ModelAttribute Store store) {
        storeService.updateStore(store);
        return "redirect:/stores";
    }
}
