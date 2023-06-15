package com.tyrontundrom.eatforit.dto;

import javax.annotation.Nullable;
import java.util.List;

class DelivererDto extends EmployeeDto {

    @Nullable
    private List<OrderDto> orders;

    @Nullable
    public List<OrderDto> getOrders() {
        return orders;
    }

    public void setOrders(@Nullable List<OrderDto> orders) {
        this.orders = orders;
    }
}
