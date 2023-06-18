package com.tyrontundrom.eatforit.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Dish {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    @NotNull
    private UUID uuid;

    @NotNull
    @Min(0)
    private Integer quantity;

    @NotNull
    @OneToOne
    private Product product;

    @Nullable
    @ManyToMany(mappedBy = "dishes")
    private List<MenuItem> menuItems;

}
