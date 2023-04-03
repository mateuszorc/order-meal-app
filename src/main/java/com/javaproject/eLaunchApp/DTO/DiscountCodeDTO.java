package com.javaproject.eLaunchApp.DTO;

import com.javaproject.eLaunchApp.models.Period;
import com.javaproject.eLaunchApp.models.enums.DiscountUnit;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@GeneratePojoBuilder
public class DiscountCodeDTO {

    @NotNull
    private UUID uuid;

    @NotBlank
    private String code;

    @Column(scale = 2, precision = 12)
    @Digits(integer = 10, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal discount;

    @NotNull
    private DiscountUnit discountUnit;

    @Nullable
    private List<UserDTO> users;

    @NotNull
    private Period period;

    @Nullable
    private List<RestaurantDTO> restaurantDTOS;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public DiscountUnit getDiscountUnit() {
        return discountUnit;
    }

    public void setDiscountUnit(DiscountUnit discountUnit) {
        this.discountUnit = discountUnit;
    }

    @Nullable
    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(@Nullable List<UserDTO> users) {
        this.users = users;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    @Nullable
    public List<RestaurantDTO> getRestaurantDTOS() {
        return restaurantDTOS;
    }

    public void setRestaurantDTOS(@Nullable List<RestaurantDTO> restaurantDTOS) {
        this.restaurantDTOS = restaurantDTOS;
    }
}
