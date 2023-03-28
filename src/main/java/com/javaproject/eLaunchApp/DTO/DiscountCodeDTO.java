package com.javaproject.eLaunchApp.DTO;

import com.javaproject.eLaunchApp.models.User;
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
    private List<User> users;

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
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(@Nullable List<User> users) {
        this.users = users;
    }

    @Nullable
    public List<RestaurantDTO> getRestaurants() {
        return restaurantDTOS;
    }

    public void setRestaurants(@Nullable List<RestaurantDTO> restaurantDTOS) {
        this.restaurantDTOS = restaurantDTOS;
    }
}
