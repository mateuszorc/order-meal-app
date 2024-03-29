package com.javaproject.eLaunchApp.DTO;

import com.fasterxml.jackson.annotation.JsonView;
import com.javaproject.eLaunchApp.models.User;
import com.javaproject.eLaunchApp.models.enums.EvidenceType;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
@GeneratePojoBuilder
public class OperationEvidenceDTO {
    public static class View{
        public interface Basic{}
        public interface Extended extends Basic {}
    }


    @JsonView(View.Basic.class)
    @NotNull
    private Instant date;

    @JsonView(View.Basic.class)
    @NotNull
    private EvidenceType type;

    @JsonView(View.Extended.class)
    @NotNull
    @Column(scale = 2, precision = 12)
    @Digits(integer = 10, fraction = 2)
    @Min(0)
    private BigDecimal amount;

    @JsonView(View.Extended.class)
    @NotNull
    private UserDTO user;

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public EvidenceType getType() {
        return type;
    }

    public void setType(EvidenceType type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
