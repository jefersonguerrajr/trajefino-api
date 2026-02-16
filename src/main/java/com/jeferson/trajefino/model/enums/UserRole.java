package com.jeferson.trajefino.model.enums;


public enum UserRole {
    ROLE_ADMIN("ROLE_ADMIN", "Administrador"),
    ROLE_OPERATOR("ROLE_OPERATOR", "Operador"),
    ROLE_CUSTOMER("ROLE_CUSTOMER", "Cliente");

    private final String role;
    private final String description;

    UserRole(String role, String description) {
        this.role = role;
        this.description = description;
    }

    public String getRole() {
        return role;
    }

    public String getDescription() {
        return description;
    }
}
