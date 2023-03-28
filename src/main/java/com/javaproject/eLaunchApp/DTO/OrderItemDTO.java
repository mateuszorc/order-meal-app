package com.javaproject.eLaunchApp.DTO;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;


public class OrderItemDTO {

    @NotNull
    private UUID uuid;

    @NotNull
    @Min(1)
    private Integer quantity;

    @NotNull
    private MenuItemDTO menuItemDTO;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public MenuItemDTO getMenuItem() {
        return menuItemDTO;
    }

    public void setMenuItem(MenuItemDTO menuItemDTO) {
        this.menuItemDTO = menuItemDTO;
    }
}
