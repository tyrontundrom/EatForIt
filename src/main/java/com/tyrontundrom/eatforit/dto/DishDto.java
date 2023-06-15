package com.tyrontundrom.eatforit.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class DishDto {

    @NotNull
    private UUID uuid;

    @NotNull
    @Min(0)
    private Integer quantity;

    @NotNull
    private ProductDto productDto;

    @Nullable
    private List<MenuItemDto> menuItemDtos;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ProductDto getProductDto() {
        return productDto;
    }

    public void setProductDto(ProductDto productDto) {
        this.productDto = productDto;
    }

    @Nullable
    public List<MenuItemDto> getMenuItemDtos() {
        return menuItemDtos;
    }

    public void setMenuItemDtos(@Nullable List<MenuItemDto> menuItemDtos) {
        this.menuItemDtos = menuItemDtos;
    }
}
