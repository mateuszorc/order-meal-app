package com.javaproject.eLaunchApp.DTO;

import com.javaproject.eLaunchApp.models.enums.Archive;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@GeneratePojoBuilder
public class UserDTO {

    @NotNull
    private UUID uuid;

    @NotNull
    private PersonalDataDTO personalData;

    @NotNull
    private List<DeliveryAddressDTO> addresses;

    @NotNull
    private LoginDataDTO loginData;

    @Nullable
    private List<OrderDTO> orderDTOS;

    @NotNull
    private List<OperationEvidenceDTO> operationEvidences;

    @Nullable
    private List<DiscountCodeDTO> discountCode;

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
