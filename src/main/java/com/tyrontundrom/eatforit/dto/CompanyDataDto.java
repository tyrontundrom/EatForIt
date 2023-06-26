package com.tyrontundrom.eatforit.dto;

import com.fasterxml.jackson.annotation.JsonView;
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
public class CompanyDataDto {

    public static class View {
        public interface Basic {}
        public interface Extended extends Basic {}
    }

    @JsonView(View.Basic.class)
    @NotNull
    private String name;

    @JsonView(View.Extended.class)
    @Embedded
    @NotNull
    private AddressDto addressDto;

    @JsonView(View.Extended.class)
    @NotNull
    private String NIP;

    @JsonView(View.Extended.class)
    @NotNull
    private String REGON;

    @JsonView(View.Extended.class)
    @NotNull
    private String phone;

    @JsonView(View.Extended.class)
    @NotNull
    private String email;


}
