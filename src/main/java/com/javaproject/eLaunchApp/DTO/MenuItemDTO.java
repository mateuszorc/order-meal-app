package com.javaproject.eLaunchApp.DTO;

import com.fasterxml.jackson.annotation.JsonView;
import com.javaproject.eLaunchApp.models.enums.VatTax;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
@GeneratePojoBuilder
public class MenuItemDTO {
    public static class View{
        public interface Basic{}
        public interface Extended extends Basic {}
    }


    @JsonView(View.Basic.class)
    @NotNull
    private UUID uuid;

    @JsonView(View.Basic.class)
    @NotBlank
    private String name;

    @JsonView(View.Extended.class)
    @Column(scale = 2, precision = 12)
    @Digits(integer = 10, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal nettoPrice;

    @JsonView(View.Extended.class)
    @NotNull
    private VatTax vatTax;

    @JsonView(View.Extended.class)
    @Column(scale = 2, precision = 12)
    @Digits(integer = 10, fraction = 2)
    @NotNull
    private BigDecimal bruttoPrice;

    @JsonView(View.Extended.class)
    @NotNull
    @Size(max = 1)
    private List<DishDTO> dishDTOS;

    @JsonView(View.Extended.class)
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
