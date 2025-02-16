package com.br.wallaceartur.DevConnect.enums;

public enum UserRole {
    USER("USER_ROLE"),
    ADMIN("ADMIN_ROLE");


    private String userRole;

    UserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserRole(){
        return userRole;
    }
}
