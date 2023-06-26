package com.tyrontundrom.eatforit.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import javax.annotation.Nullable;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryAddressDto {

    @NotNull
    private UUID uuid;
    @NotNull
    private String description;
    @NotNull
    private String street;
    @NotNull
    private String streetNumber;
    @NotNull
    private String localNumber;
    @NotNull
    private String city;
    @Nullable
    private String borough;
    @Nullable
    private String country;
    @Nullable
    private String state;
    @NotNull
    private UserDto userDto;


}
