package com.tyrontundrom.eatforit.dto;

import com.fasterxml.jackson.annotation.JsonView;
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

    public static class View {
        public interface Basic {}
        public interface Extended extends Basic {}
    }

    @JsonView(View.Basic.class)
    @NotNull
    private UUID uuid;

    @JsonView(View.Basic.class)
    @NotNull
    private String description;

    @JsonView(View.Extended.class)
    @NotNull
    private String street;

    @JsonView(View.Extended.class)
    @NotNull
    private String streetNumber;

    @JsonView(View.Extended.class)
    @NotNull
    private String localNumber;

    @JsonView(View.Extended.class)
    @NotNull
    private String postalCode;

    @JsonView(View.Extended.class)
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

    @JsonView(View.Extended.class)
    @NotNull
    private UserDto userDto;


}
