package com.javaproject.eLaunchApp.DTO;


import com.fasterxml.jackson.annotation.JsonView;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;

@Embeddable
@GeneratePojoBuilder
public class CompanyDataDTO {

    public static class View{
        public interface Basic{}
        public interface Extended extends AddressDTO.View.Basic {}
    }

    @JsonView(View.Basic.class)
    @NotNull
    private String name;

    @JsonView(View.Extended.class)
    @Embedded
    @NotNull
    private AddressDTO addressDTO;

    @JsonView(View.Extended.class)
    @NotNull
    private String nip;

    @JsonView(View.Extended.class)
    @NotNull
    private String region;

    @JsonView(View.Extended.class)
    @NotNull
    private String phone;

    @JsonView(View.Extended.class)
    @NotNull
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressDTO getAddress() {
        return addressDTO;
    }

    public void setAddress(AddressDTO addressDTO) {
        this.addressDTO = addressDTO;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
