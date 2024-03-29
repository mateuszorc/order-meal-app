package com.javaproject.eLaunchApp.models;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Embeddable
@GeneratePojoBuilder
public class LoginData {

    @Column(unique = true)
    @Size(min = 3)
    private String login;

    @Pattern(regexp = "(?=.*(a-Z))(?=.*(A-Z))(?=.*\\d).{6,}$")
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
