package com.javaproject.eLaunchApp.DTO;

import com.javaproject.eLaunchApp.models.enums.VatTax;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
@GeneratePojoBuilder
public class MenuItemDTO {

    @NotNull
    private UUID uuid;

    @NotBlank
    private String name;

    @Column(scale = 2, precision = 12)
    @Digits(integer = 10, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal nettoPrice;

    @NotNull
    private VatTax vatTax;

    @Column(scale = 2, precision = 12)
    @Digits(integer = 10, fraction = 2)
    @NotNull
    private BigDecimal bruttoPrice;

    @NotNull
    @Size(max = 1)
    private List<DishDTO> dishDTOS;

    @NotNull
    private RestaurantDTO restaurants;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getNettoPrice() {
        return nettoPrice;
    }

    public void setNettoPrice(BigDecimal nettoPrice) {
        this.nettoPrice = nettoPrice;
    }

    public VatTax getVatTax() {
        return vatTax;
    }

    public void setVatTax(VatTax vatTax) {
        this.vatTax = vatTax;
    }

    public BigDecimal getBruttoPrice() {
        return bruttoPrice;
    }

    public void setBruttoPrice(BigDecimal bruttoPrice) {
        this.bruttoPrice = bruttoPrice;
    }

    public List<DishDTO> getDishes() {
        return dishDTOS;
    }

    public void setDishes(List<DishDTO> dishDTOS) {
        this.dishDTOS = dishDTOS;
    }

    public RestaurantDTO getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(RestaurantDTO restaurants) {
        this.restaurants = restaurants;
    }
}
