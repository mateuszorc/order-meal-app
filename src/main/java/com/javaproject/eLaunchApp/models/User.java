package com.javaproject.eLaunchApp.models;

import com.javaproject.eLaunchApp.models.enums.Archive;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.UUID;

@Entity
@GeneratePojoBuilder
public class User {

    @Id
    @GeneratedValue
    @NotNull
    private Long id;

    @Column(unique = true)
    @NotNull
    private UUID uuid;

    @NotNull
    @Embedded
    private PersonalData personalData;

    @Nullable
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeliveryAddress> addresses;

    @NotNull
    @Embedded
    private LoginData loginData;

    @Nullable
    @OneToMany(mappedBy = "user")
    private List<Order> orderDTOS;

    @NotNull
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OperationEvidence> operationEvidences;


    @Nullable
    @ManyToMany(mappedBy = "users")
    private List<DiscountCode> discountCode;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Archive archive;

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

    public PersonalData getPersonalData() {
        return personalData;
    }

    public void setPersonalData(PersonalData personalData) {
        this.personalData = personalData;
    }

    public List<DeliveryAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<DeliveryAddress> addresses) {
        this.addresses = addresses;
    }

    public LoginData getLoginData() {
        return loginData;
    }

    public void setLoginData(LoginData loginData) {
        this.loginData = loginData;
    }

    @Nullable
    public List<Order> getOrders() {
        return orderDTOS;
    }

    public void setOrders(@Nullable List<Order> orderDTOS) {
        this.orderDTOS = orderDTOS;
    }

    public List<OperationEvidence> getOperationEvidences() {
        return operationEvidences;
    }

    public void setOperationEvidences(List<OperationEvidence> operationEvidences) {
        this.operationEvidences = operationEvidences;
    }

    @Nullable
    public List<DiscountCode> getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(@Nullable List<DiscountCode> discountCode) {
        this.discountCode = discountCode;
    }

    public Archive getArchive() {
        return archive;
    }

    public void setArchive(Archive archive) {
        this.archive = archive;
    }
}
