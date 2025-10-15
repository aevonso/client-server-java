package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.model.CoffeeType;
import org.example.model.dto.CoffeeTypeRequest;
import org.example.repository.CoffeeTypeRepository;
import org.example.service.CoffeeTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CoffeeTypeServiceImpl implements CoffeeTypeService {

    private final CoffeeTypeRepository coffeeTypeRepository;

    @Override
    @Transactional
    public CoffeeType createCoffeeType(CoffeeTypeRequest request) {
        if (coffeeTypeRepository.existsByName(request.getName())) {
            throw new RuntimeException("Coffee type with this name already exists");
        }

        CoffeeType coffeeType = new CoffeeType();
        coffeeType.setName(request.getName());
        coffeeType.setDescription(request.getDescription());
        coffeeType.setPrice(request.getPrice());
        coffeeType.setIsAvailable(request.getIsAvailable());
        coffeeType.setCreatedAt(LocalDateTime.now());

        return coffeeTypeRepository.save(coffeeType);
    }

    @Override
    public List<CoffeeType> getAllCoffeeTypes() {
        return coffeeTypeRepository.findAll();
    }

    @Override
    @Transactional
    public CoffeeType updateCoffeeType(Long coffeeTypeId, CoffeeTypeRequest request) {
        CoffeeType existingCoffeeType = coffeeTypeRepository.findById(coffeeTypeId)
                .orElseThrow(() -> new RuntimeException("Coffee type not found"));

        if (!existingCoffeeType.getName().equals(request.getName()) &&
                coffeeTypeRepository.existsByName(request.getName())) {
            throw new RuntimeException("Coffee type with this name already exists");
        }

        existingCoffeeType.setName(request.getName());
        existingCoffeeType.setDescription(request.getDescription());
        existingCoffeeType.setPrice(request.getPrice());
        existingCoffeeType.setIsAvailable(request.getIsAvailable());

        return coffeeTypeRepository.save(existingCoffeeType);
    }

    @Override
    @Transactional
    public CoffeeType updateCoffeeTypeName(Long coffeeTypeId, String newName) {
        CoffeeType existingCoffeeType = coffeeTypeRepository.findById(coffeeTypeId)
                .orElseThrow(() -> new RuntimeException("Coffee type not found"));

        if (coffeeTypeRepository.existsByName(newName)) {
            throw new RuntimeException("Coffee type with this name already exists");
        }

        existingCoffeeType.setName(newName);
        return coffeeTypeRepository.save(existingCoffeeType);
    }
}
