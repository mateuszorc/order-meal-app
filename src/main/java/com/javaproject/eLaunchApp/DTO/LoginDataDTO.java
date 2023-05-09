package com.javaproject.eLaunchApp.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Embeddable
@GeneratePojoBuilder
public class LoginDataDTO {
    public static class View{
        public interface Basic{}
    }


    @JsonView(View.Basic.class)
    @Size(min = 3)
    private String login;

    @JsonIgnore
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
