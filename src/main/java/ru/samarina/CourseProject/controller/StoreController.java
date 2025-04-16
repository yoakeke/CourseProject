package ru.samarina.CourseProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.samarina.CourseProject.entity.Store;
import ru.samarina.CourseProject.service.StoreService;

import java.util.List;

@PreAuthorize("hasRole('ADMIN')")
@Controller
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping
    public String listStores(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Store> stores = (keyword != null && !keyword.isEmpty()) ?
                storeService.searchStores(keyword) : storeService.getAllStores();
        model.addAttribute("stores", stores);
        return "stores";
    }

    @PostMapping("/addStore")
    public String addStore(@RequestParam String name) {
        storeService.addStore(Store.builder().name(name).build());
        return "redirect:/stores";
    }

    @GetMapping("/delete/{id}")
    public String deleteStore(@PathVariable Long id) {
        storeService.deleteStore(id);
        return "redirect:/stores";
    }

    @GetMapping("/edit/{id}")
    public String editStorePage(@PathVariable Long id, Model model) {
        Store store = storeService.getStoreById(id);
        model.addAttribute("store", store);
        return "edit-store"; // страничка с формой для редактирования
    }

    @PostMapping("/update")
    public String updateStore(@ModelAttribute Store store) {
        storeService.updateStore(store);
        return "redirect:/stores";
    }
}
