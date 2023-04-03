package com.javaproject.eLaunchApp.DTO;

import com.javaproject.eLaunchApp.models.User;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
@GeneratePojoBuilder
public class OrderDTO {

    @NotNull
    private UUID uuid;

    @Digits(integer = 10, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal nettoPrice;

    @Digits(integer = 10, fraction = 2)
    @NotNull
    private BigDecimal bruttoPrice;

    @Nullable
    private DiscountCodeDTO discountCodeDTO;

    @NotNull
    private DeliveryAddressDTO deliveryAddressDTO;

    @Digits(integer = 10, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal amountToPayBrutto;

    @Nullable
    private String note;

    @Embedded
    private OrderStatusDTO orderStatusDTO;

    @NotNull
    @Size(min = 1)
    private List<OrderItemDTO> orderItems;

    @NotNull
    private UserDTO user;

    @NotNull
    private DelivererDTO delivererDTO;

    @NotNull
    private RestaurantDTO restaurantDTO;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public BigDecimal getNettoPrice() {
        return nettoPrice;
    }

    public void setNettoPrice(BigDecimal nettoPrice) {
        this.nettoPrice = nettoPrice;
    }

    public BigDecimal getBruttoPrice() {
        return bruttoPrice;
    }

    public void setBruttoPrice(BigDecimal bruttoPrice) {
        this.bruttoPrice = bruttoPrice;
    }

    @Nullable
    public DiscountCodeDTO getDiscountCodeDTO() {
        return discountCodeDTO;
    }

    public void setDiscountCodeDTO(@Nullable DiscountCodeDTO discountCodeDTO) {
        this.discountCodeDTO = discountCodeDTO;
    }

    public DeliveryAddressDTO getDeliveryAddressDTO() {
        return deliveryAddressDTO;
    }

    public void setDeliveryAddressDTO(DeliveryAddressDTO deliveryAddressDTO) {
        this.deliveryAddressDTO = deliveryAddressDTO;
    }

    public BigDecimal getAmountToPayBrutto() {
        return amountToPayBrutto;
    }

    public void setAmountToPayBrutto(BigDecimal amountToPayBrutto) {
        this.amountToPayBrutto = amountToPayBrutto;
    }

    @Nullable
    public String getNote() {
        return note;
    }

    public void setNote(@Nullable String note) {
        this.note = note;
    }

    public OrderStatusDTO getOrderStatusDTO() {
        return orderStatusDTO;
    }

    public void setOrderStatusDTO(OrderStatusDTO orderStatusDTO) {
        this.orderStatusDTO = orderStatusDTO;
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public DelivererDTO getDelivererDTO() {
        return delivererDTO;
    }

    public void setDelivererDTO(DelivererDTO delivererDTO) {
        this.delivererDTO = delivererDTO;
    }

    public RestaurantDTO getRestaurantDTO() {
        return restaurantDTO;
    }

    public void setRestaurantDTO(RestaurantDTO restaurantDTO) {
        this.restaurantDTO = restaurantDTO;
    }
}
