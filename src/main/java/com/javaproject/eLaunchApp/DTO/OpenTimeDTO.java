package com.javaproject.eLaunchApp.DTO;

import com.javaproject.eLaunchApp.models.enums.DayOfWeek;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class OpenTimeDTO {

    @NotNull
    private UUID uuid;

    @NotNull
    private DayOfWeek dayOfWeek;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public PeriodTimeDTO getPeriodTime() {
        return periodTimeDTO;
    }

    public void setPeriodTime(PeriodTimeDTO periodTimeDTO) {
        this.periodTimeDTO = periodTimeDTO;
    }

    public RestaurantDTO getRestaurant() {
        return restaurantDTO;
    }

    public void setRestaurant(RestaurantDTO restaurantDTO) {
        this.restaurantDTO = restaurantDTO;
    }

    @NotNull
    @Embedded
    private PeriodTimeDTO periodTimeDTO;

    @NotNull
    @ManyToOne
    private RestaurantDTO restaurantDTO;
}
