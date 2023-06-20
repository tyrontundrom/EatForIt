package com.tyrontundrom.eatforit.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import javax.annotation.Nullable;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DeliveryAddress {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
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
    @ManyToOne
    private User user;
}
