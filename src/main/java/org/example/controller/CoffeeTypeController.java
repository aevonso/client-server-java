package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.CoffeeType;
import org.example.model.dto.CoffeeTypeRequest;
import org.example.service.CoffeeTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/coffee-types")
@RequiredArgsConstructor
public class CoffeeTypeController {

    private final CoffeeTypeService coffeeTypeService;

    @PostMapping
    public ResponseEntity<CoffeeType> createCoffeeType(@Valid @RequestBody CoffeeTypeRequest request) {
        CoffeeType coffeeType = coffeeTypeService.createCoffeeType(request);
        return ResponseEntity.ok(coffeeType);
    }

    @GetMapping
    public ResponseEntity<List<CoffeeType>> getAllCoffeeTypes() {
        List<CoffeeType> coffeeTypes = coffeeTypeService.getAllCoffeeTypes();
        return ResponseEntity.ok(coffeeTypes);
    }

    @PutMapping("/{coffeeTypeId}")
    public ResponseEntity<CoffeeType> updateCoffeeType(
            @PathVariable Long coffeeTypeId,
            @Valid @RequestBody CoffeeTypeRequest request) {
        CoffeeType coffeeType = coffeeTypeService.updateCoffeeType(coffeeTypeId, request);
        return ResponseEntity.ok(coffeeType);
    }

    @PatchMapping("/{coffeeTypeId}/name")
    public ResponseEntity<CoffeeType> updateCoffeeTypeName(
            @PathVariable Long coffeeTypeId,
            @RequestParam String newName) {
        CoffeeType coffeeType = coffeeTypeService.updateCoffeeTypeName(coffeeTypeId, newName);
        return ResponseEntity.ok(coffeeType);
    }
}
