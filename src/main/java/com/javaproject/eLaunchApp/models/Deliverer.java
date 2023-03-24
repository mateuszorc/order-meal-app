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
    @OneToMany
    private List<Order> orders;

    @Nullable
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(@Nullable List<Order> orders) {
        this.orders = orders;
    }
}
