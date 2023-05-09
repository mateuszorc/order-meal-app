package com.javaproject.eLaunchApp.DTO;

import com.fasterxml.jackson.annotation.JsonView;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Embeddable
@GeneratePojoBuilder
public class OrderStatusDTO {
    public static class View{
        public interface Basic{}
    }


    @JsonView(View.Basic.class)
    @NotNull
    private Instant orderTime;

    @JsonView(View.Basic.class)
    @NotNull
    private Boolean isPaid;

    @JsonView(View.Basic.class)
    @NotNull
    private Instant giveOutTime;

    @JsonView(View.Basic.class)
    @NotNull
    private Instant deliveryTime;

    public Instant getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Instant orderTime) {
        this.orderTime = orderTime;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public Instant getGiveOutTime() {
        return giveOutTime;
    }

    public void setGiveOutTime(Instant giveOutTime) {
        this.giveOutTime = giveOutTime;
    }

    public Instant getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Instant deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
}
