package com.javaproject.eLaunchApp.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.javaproject.eLaunchApp.models.enums.Archive;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@GeneratePojoBuilder
public class UserDTO {
    public static class View{
        public interface Id{}
        public interface Basic extends Id {}
        public interface Extended extends Basic {}
    }


    @JsonView(View.Id.class)
    @NotNull
    private UUID uuid;

    @JsonView(View.Basic.class)
    @NotNull
    private PersonalDataDTO personalData;

    @JsonView(View.Extended.class)
    @NotNull
    private List<DeliveryAddressDTO> addresses;

    @JsonView(View.Extended.class)
    @NotNull
    private LoginDataDTO loginData;

    @JsonIgnore
    @Nullable
    private List<OrderDTO> orderDTOS;

    @JsonView(View.Extended.class)
    @NotNull
    private List<OperationEvidenceDTO> operationEvidences;

    @JsonView(View.Extended.class)
    @Nullable
    private List<DiscountCodeDTO> discountCode;

    @JsonView(View.Extended.class)
    @NotNull
    private Archive archive;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public PersonalDataDTO getPersonalData() {
        return personalData;
    }

    public void setPersonalData(PersonalDataDTO personalData) {
        this.personalData = personalData;
    }

    public List<DeliveryAddressDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<DeliveryAddressDTO> addresses) {
        this.addresses = addresses;
    }

    public LoginDataDTO getLoginData() {
        return loginData;
    }

    public void setLoginData(LoginDataDTO loginData) {
        this.loginData = loginData;
    }

    @Nullable
    public List<OrderDTO> getOrderDTOS() {
        return orderDTOS;
    }

    public void setOrderDTOS(@Nullable List<OrderDTO> orderDTOS) {
        this.orderDTOS = orderDTOS;
    }

    public List<OperationEvidenceDTO> getOperationEvidences() {
        return operationEvidences;
    }

    public void setOperationEvidences(List<OperationEvidenceDTO> operationEvidences) {
        this.operationEvidences = operationEvidences;
    }

    @Nullable
    public List<DiscountCodeDTO> getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(@Nullable List<DiscountCodeDTO> discountCode) {
        this.discountCode = discountCode;
    }

    public Archive getArchive() {
        return archive;
    }

    public void setArchive(Archive archive) {
        this.archive = archive;
    }
}
