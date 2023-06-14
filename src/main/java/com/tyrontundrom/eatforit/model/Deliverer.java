package com.tyrontundrom.eatforit.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import javax.annotation.Nullable;
import java.util.List;

@Entity
@DiscriminatorValue("deliverer")
class Deliverer extends Employee {

    @Nullable
    @OneToMany(mappedBy = "deliverer")
    private List<Order> orders;

    @Nullable
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(@Nullable List<Order> orders) {
        this.orders = orders;
    }
}
