package com.tyrontundrom.eatforit.dto;

import com.fasterxml.jackson.annotation.JsonView;
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
public class AddressDto {

    public static class View {
        public interface Basic {}
        public interface Extended extends Basic {}
    }

    @JsonView(View.Basic.class)
    @NotNull
    private String street;
    @JsonView(View.Basic.class)
    @NotNull
    private String streetNumber;
    @JsonView(View.Basic.class)
    @NotNull
    private String localNumber;
    @JsonView(View.Basic.class)
    @NotNull
    private String postalCode;
    @JsonView(View.Basic.class)
    @NotNull
    private String city;
    @JsonView(View.Extended.class)
    @Nullable
    private String borough;
    @JsonView(View.Extended.class)
    @Nullable
    private String country;
    @JsonView(View.Extended.class)
    @Nullable
    private String state;


}
