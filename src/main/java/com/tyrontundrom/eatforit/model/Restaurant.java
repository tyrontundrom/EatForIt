package com.tyrontundrom.eatforit.model;

import com.tyrontundrom.eatforit.model.enums.Archive;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Restaurant {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    @NotNull
    private UUID uuid;

    @NotBlank
    private String name;

    @NotNull
    @Embedded
    private LogginData logginData;

    @NotNull
    @Embedded
    private CompanyData companyData;

    @NotNull
    @Size(max = 7)
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Opentime> opentimes;

    @NotNull
    @OneToMany(mappedBy = "restaurant")
    private List<Order> orders;

    @NotNull
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuItem> menuItems;

    @NotNull
    @ManyToMany(mappedBy = "restaurants")
    private List<DiscountCode> discountCodes;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Archive archive;
}
