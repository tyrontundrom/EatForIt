
package com.tyrontundrom.eatforit.service;

import com.tyrontundrom.eatforit.dto.DelivererDto;
import com.tyrontundrom.eatforit.dto.ProductDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {
    List<ProductDto> getAll();
    void put(UUID uuid, ProductDto productDto);
    void delete(UUID uuid);
    Optional<ProductDto> getByUuid(UUID uuid);
}
