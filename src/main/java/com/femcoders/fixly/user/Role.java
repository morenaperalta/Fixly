package com.femcoders.fixly.user;

public enum Role {
    DEFAULT,
    ADMIN,
    CLIENT,
    TECHNICIAN,
    SUPERVISOR;

    public String getName() {
        return "ROLE_" + this.name();
    }
}
