package com.br.wallaceartur.DevConnect.dtos;

public class LoginRequest {

    private String username;

    private String passsword;

    public LoginRequest(String username, String passsword) {
        this.username = username;
        this.passsword = passsword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasssword() {
        return passsword;
    }

    public void setPasssword(String passsword) {
        this.passsword = passsword;
    }
}
