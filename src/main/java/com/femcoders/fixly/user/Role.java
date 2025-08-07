package com.femcoders.fixly.user;

public enum Role {
    ADMIN,
    USER,
    TECHNICIAN,
    SUPERVISOR;

    public String getName() {
        return "ROLE_" + this.name();
    }
}
