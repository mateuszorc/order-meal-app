package com.javaproject.eLaunchApp.models;

import javax.annotation.Nullable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@DiscriminatorValue("deliverer")
public class Deliverer extends Employee{

    @Nullable
    @OneToMany(mappedBy = "deliverer")
    private List<Order> orderDTOS;

    @Nullable
    public List<Order> getOrders() {
        return orderDTOS;
    }

    public void setOrders(@Nullable List<Order> orderDTOS) {
        this.orderDTOS = orderDTOS;
    }
}
