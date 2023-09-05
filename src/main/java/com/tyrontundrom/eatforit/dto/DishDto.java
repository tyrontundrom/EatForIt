package com.tyrontundrom.eatforit.dto;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DishDto {

    public static class View {
        public interface Id {}
        public interface Basic extends Id {}

        public interface Extended extends Basic {}
    }

    public interface DataUpdateValidation {}

    @JsonView(View.Id.class)
    @NotNull
    private UUID uuid;

    @JsonView(View.Extended.class)
    @NotNull
    @Min(0)
    private Integer quantity;

    @JsonView(View.Extended.class)
    @NotNull
    private ProductDto productDto;

    @JsonView(View.Extended.class)
    @Nullable
    @Null(groups = DataUpdateValidation.class)
    private List<MenuItemDto> menuItemDtos;

    }
