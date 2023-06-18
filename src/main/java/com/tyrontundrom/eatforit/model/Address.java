package com.tyrontundrom.eatforit.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import javax.annotation.Nullable;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
class Address {

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
}
