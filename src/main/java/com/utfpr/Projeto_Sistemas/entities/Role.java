package com.utfpr.Projeto_Sistemas.entities;

public enum Role {

    USER("user"),

    COMPANY("company");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
