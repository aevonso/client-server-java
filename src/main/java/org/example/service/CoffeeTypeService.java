package org.example.service;

import org.example.model.CoffeeType;
import org.example.model.dto.CoffeeTypeRequest;

import java.util.List;

public interface CoffeeTypeService {
    CoffeeType createCoffeeType(CoffeeTypeRequest request);
    List<CoffeeType> getAllCoffeeTypes();
    CoffeeType updateCoffeeType(Long coffeeTypeId, CoffeeTypeRequest request);
    CoffeeType updateCoffeeTypeName(Long coffeeTypeId, String newName);
}
