package com.tyrontundrom.eatforit.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.tyrontundrom.eatforit.model.enums.Archive;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.*;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDto {

    public static class View {
        public interface Id {}
        public interface Basic extends Id {}
        public interface Extended extends Basic {}
    }

    public interface DataUpdateValidation {}

    @JsonView(View.Id.class)
    @NotNull
    private UUID uuid;

    @JsonView(View.Basic.class)
    @NotBlank
    private String name;

    @JsonView(View.Basic.class)
    @NotNull
    @Embedded
    private LogginDataDto logginDataDto;

    @JsonView(View.Extended.class)
    @NotNull
    @Embedded
    private CompanyDataDto companyDataDto;

    @JsonView(View.Extended.class)
    @NotNull
    @Size(max = 7)
    private List<OpenTimeDto> openTimeDtos;

    @JsonView(View.Extended.class)
    @Nullable
    @Null(groups = DataUpdateValidation.class)
    private List<OrderDto> orderDtos;

    @JsonView(View.Extended.class)
    @Nullable
    @Null(groups = DataUpdateValidation.class)
    private List<MenuItemDto> menuItemDtos;

    @JsonIgnore
    @NotNull
    private List<DiscountCodeDto> discountCodeDtos;

    @JsonView(View.Extended.class)
    @NotNull
    @Enumerated(EnumType.STRING)
    private Archive archive;


}
