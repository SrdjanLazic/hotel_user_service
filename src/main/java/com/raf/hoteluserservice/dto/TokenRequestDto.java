package com.raf.hoteluserservice.dto;

public class TokenRequestDto {

    private String email;
    private String password;

    public TokenRequestDto() {
    }

    public TokenRequestDto(String email, String password) {
        this.password = password;
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
