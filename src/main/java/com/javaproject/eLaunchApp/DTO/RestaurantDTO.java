package com.javaproject.eLaunchApp.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.javaproject.eLaunchApp.models.enums.Archive;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@GeneratePojoBuilder
public class RestaurantDTO {
    public static class View{
        public interface Id{}
        public interface Basic extends Id {}
        public interface Extended extends Basic {}
    }
    public interface DataUpdateValidation {}


    @JsonView(View.Id.class)
    @NotNull
    private UUID uuid;

    @JsonView(View.Basic.class)
    @NotNull
    private String name;

    @JsonView(View.Basic.class)
    @NotBlank
    @Embedded
    private LoginDataDTO loginDataDTO;

    @JsonView(View.Extended.class)
    @NotBlank
    @Embedded
    private CompanyDataDTO companyDataDTO;

    @JsonView(View.Extended.class)
    @NotNull
    @Size(max = 7)
    private List<OpenTimeDTO> openTimeDTOS;

    @JsonView(View.Extended.class)
    @Nullable
    @Null(groups = DataUpdateValidation.class)
    private List<OrderDTO> orderDTOS;

    @JsonView(View.Extended.class)
    @Nullable
    @Null(groups = DataUpdateValidation.class)
    private List<MenuItemDTO> menuItemDTOS;

    @JsonIgnore
    @NotNull
    private List<DiscountCodeDTO> discountCodeDTOS;

    @JsonView(View.Extended.class)
    @NotNull
    private Archive archive;

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

    public LoginDataDTO getLoginData() {
        return loginDataDTO;
    }

    public void setLoginData(LoginDataDTO loginDataDTO) {
        this.loginDataDTO = loginDataDTO;
    }

    public CompanyDataDTO getCompanyData() {
        return companyDataDTO;
    }

    public void setCompanyData(CompanyDataDTO companyDataDTO) {
        this.companyDataDTO = companyDataDTO;
    }

    public List<OpenTimeDTO> getOpenTimes() {
        return openTimeDTOS;
    }

    public void setOpenTimes(List<OpenTimeDTO> openTimeDTOS) {
        this.openTimeDTOS = openTimeDTOS;
    }

    public List<OrderDTO> getOrders() {
        return orderDTOS;
    }

    public void setOrders(List<OrderDTO> orderDTOS) {
        this.orderDTOS = orderDTOS;
    }

    public List<MenuItemDTO> getMenuItems() {
        return menuItemDTOS;
    }

    public void setMenuItems(List<MenuItemDTO> menuItemDTOS) {
        this.menuItemDTOS = menuItemDTOS;
    }

    public List<DiscountCodeDTO> getDiscountCodes() {
        return discountCodeDTOS;
    }

    public void setDiscountCodes(List<DiscountCodeDTO> discountCodeDTOS) {
        this.discountCodeDTOS = discountCodeDTOS;
    }

    public Archive getArchive() {
        return archive;
    }

    public void setArchive(Archive archive) {
        this.archive = archive;
    }
}
