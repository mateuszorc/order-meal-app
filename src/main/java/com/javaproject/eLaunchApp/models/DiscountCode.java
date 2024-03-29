package com.javaproject.eLaunchApp.models;

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

@Entity
@GeneratePojoBuilder
public class DiscountCode {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
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
    @Enumerated(EnumType.STRING)
    private DiscountUnit discountUnit;

    @Nullable
    @ManyToMany
    private List<User> users;

    @NotNull
    @Embedded
    private Period period;

    @Nullable
    @ManyToMany
    private List<Restaurant> restaurants;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    @Nullable
    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(@Nullable List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
}
