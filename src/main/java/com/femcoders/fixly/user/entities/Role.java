package com.femcoders.fixly.user.entities;

public enum Role {
    ADMIN,
    CLIENT,
    TECHNICIAN,
    SUPERVISOR;

    public String getName() {
        return "ROLE_" + this.name();
    }
}
