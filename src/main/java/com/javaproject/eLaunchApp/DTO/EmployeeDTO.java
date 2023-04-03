package com.javaproject.eLaunchApp.DTO;

import com.javaproject.eLaunchApp.models.enums.Archive;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@GeneratePojoBuilder
public class EmployeeDTO {

    @NotNull
    private UUID uuid;

    @NotNull
    @Embedded
    private PersonalDataDTO personalDataDTO;

    @NotNull
    @Embedded
    private LoginDataDTO loginDataDTO;

    @NotNull
    private Archive archive;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public PersonalDataDTO getPersonalData() {
        return personalDataDTO;
    }

    public void setPersonalData(PersonalDataDTO personalDataDTO) {
        this.personalDataDTO = personalDataDTO;
    }

    public LoginDataDTO getLoginData() {
        return loginDataDTO;
    }

    public void setLoginData(LoginDataDTO loginDataDTO) {
        this.loginDataDTO = loginDataDTO;
    }

    public Archive getArchive() {
        return archive;
    }

    public void setArchive(Archive archive) {
        this.archive = archive;
    }
}
