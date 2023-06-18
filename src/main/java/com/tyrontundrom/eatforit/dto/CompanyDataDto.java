package com.tyrontundrom.eatforit.dto;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
class CompanyDataDto {

    @NotNull
    private String name;

    @Embedded
    @NotNull
    private AddressDto addressDto;

    @NotNull
    private String NIP;

    @NotNull
    private String REGON;

    @NotNull
    private String phone;

    @NotNull
    private String email;


}
