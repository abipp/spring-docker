package com.winterhold.dto.account;

import com.winterhold.validation.Compare;
import com.winterhold.validation.UniqueUsername;

import javax.validation.constraints.NotBlank;

@Compare(message = "konfirmasi password tidak cocok", firstField = "password", secondField = "passwordConfirmation")
public class RegisterDTO {
    @NotBlank(message = "username tidak boleh kosong")
    @UniqueUsername(message = "username telah digunakan")
    private String username;
    @NotBlank(message = "password tidak boleh kosong")
    private String password;
    @NotBlank(message = "password confirmation tidak boleh kosong")
    private String passwordConfirmation;

    public RegisterDTO(){

    }

    public RegisterDTO(String username, String password, String passwordConfirmation) {
        this.username = username;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
}
