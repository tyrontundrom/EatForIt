package com.tyrontundrom.eatforit.dto;

import com.tyrontundrom.eatforit.model.*;
import com.tyrontundrom.eatforit.model.enums.Archive;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public class RestaurantDto {

    @NotNull
    private UUID uuid;

    @NotBlank
    private String name;

    @NotNull
    @Embedded
    private LogginDataDto logginDataDto;

    @NotNull
    @Embedded
    private CompanyDataDto companyDataDto;

    @NotNull
    @Size(max = 7)
    private List<OpenTimeDto> openTimeDtos;

    @NotNull
    private List<OrderDto> orderDtos;

    @NotNull
    private List<MenuItem> menuItems;

    @NotNull
    private List<DiscountCodeDto> discountCodeDtos;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Archive archive;


}
